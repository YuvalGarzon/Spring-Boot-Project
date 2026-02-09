package demo.BusinessLogic;

import demo.Controller.CommandBoundary;
import demo.Controller.CommandIDBoundary;
import demo.Exception.ForbiddenException;
import demo.Exception.NotFoundException;
import demo.Controller.ObjectBoundary;
import demo.Controller.ObjectIDBoundary;
import demo.Repository.ObjectStatus;
import demo.Exception.BadRequestException;
import demo.Repository.CommandCrud;
import demo.Repository.CommandEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import demo.Web.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CommandLogicImpl implements CommandLogic {
    private final UserLogic userLogic;
    private final ObjectLogicWithRelations objectLogic;
    private CommandConverter commandConverter;
    private CommandCrud commandCrud;
    private String appName;
    private TcpListenerService sensorLogic;

    public CommandLogicImpl(CommandCrud commandCrud, CommandConverter commandConverter, UserLogic userLogic,
                            ObjectLogicWithRelations objectLogic, TcpListenerService sensorLogic) {
        this.commandCrud = commandCrud;
        this.commandConverter = commandConverter;
        this.userLogic = userLogic;
        this.objectLogic = objectLogic;
        this.sensorLogic = sensorLogic;
    }

    @Value("${spring.application.name:defaultName}")
    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    @Transactional(readOnly = false)
    public List<ObjectBoundary> invokeCommand(CommandBoundary command) throws BadRequestException, ForbiddenException {

        String userSystemID = command.getInvokedBy().getSystemID();
        String userEmail = command.getInvokedBy().getEmail();

        boolean auth = this.userLogic.authEndUser(userSystemID, userEmail);

        if (!auth) {
            throw new ForbiddenException("Only system users can invoke commands");
        }

        if (command.getCommand() == null || command.getCommand().trim().isEmpty()) {
            throw new BadRequestException("Command cannot be null or empty");
        }
        command.setCommandId(new CommandIDBoundary(UUID.randomUUID().toString(), this.appName));
        CommandEntity commandEntity = this.commandConverter.toEntity(command);
        this.commandCrud.save(commandEntity);

        List<ObjectBoundary> returnedObjects = new ArrayList<>();
        ObjectBoundary activity;
        switch (command.getCommand()) {
            case "login":
                String type = "trainee";
                ObjectBoundary trainee = this.objectLogic.getSpecificObjectByTypeAndUserEmail(type, userEmail)
                        .orElseThrow(() -> new NotFoundException("Could not find trainee with user email : " + userEmail));

                returnedObjects.add(trainee);
                break;
            case "start-session":

                // Validate input
                if (command.getTargetObject() == null)
                    throw new BadRequestException("Missing targetObject (trainee)");

                // check it is trainee

                ObjectBoundary parentObject = this.objectLogic
                        .getSpecificObject(command.getTargetObject().getObjectId(), userSystemID, userEmail)
                        .orElseThrow(() -> new BadRequestException(
                                "There is not trainee with ID: " + command.getTargetObject().getObjectId()));

                if (!"trainee".equalsIgnoreCase(parentObject.getType())) {
                    throw new BadRequestException("Target object must be of type 'trainee'");
                }

                // Build session object

                ObjectBoundary session = new ObjectBoundary();
                session.setId(new ObjectIDBoundary());
                session.setType("session");
                session.setAlias("TBD"); // Not in use
                session.setStatus(ObjectStatus.DEFAULT); // Not in use
                session.setActive(true);
                session.setCreationTimestamp(new Date());

                // Set creator
                session.setCreatedBy(command.getInvokedBy());

                // Set empty details or default
                Map<String, Object> details = new HashMap<>();
                details.put("Finished", null); // or leave it out entirely
                session.setDetails(details);

                // Create the session in DB
                ObjectBoundary createdSession = this.objectLogic.createNewObject(session, userSystemID, userEmail);

                System.out.println(createdSession);

                this.objectLogic.bindObjects(command.getTargetObject().getObjectId(), createdSession.getId().getObjectId(),
                        userSystemID, userEmail);

                returnedObjects.add(createdSession);

                break;
            case "start-activity":

                if (command.getTargetObject() == null)
                    throw new BadRequestException("Missing targetObject (session)");

                ObjectBoundary sessionObject = this.objectLogic
                        .getSpecificObject(command.getTargetObject().getObjectId(), userSystemID, userEmail)
                        .orElseThrow(() -> new BadRequestException(
                                "No session found with ID: " + command.getTargetObject().getObjectId()));

                if (!"session".equalsIgnoreCase(sessionObject.getType()))
                    throw new BadRequestException("Target object must be of type 'session'");

                // Extract equipmentId from commandAttributes
                Object equipmentIdRaw = command.getCommandAttributes().get("equipmentId");
                if (equipmentIdRaw == null)
                    throw new BadRequestException("Missing 'equipmentId' in commandAttributes");
                String equipmentId = equipmentIdRaw.toString();

                // Extract activityName (alias)
                Object activityNameRaw = command.getCommandAttributes().get("activityName");
                if (activityNameRaw == null)
                    throw new BadRequestException("Missing 'activityName' in commandAttributes");
                String activityName = activityNameRaw.toString();

                // Load and validate equipment
                ObjectBoundary equipment = this.objectLogic.getSpecificObject(equipmentId, userSystemID, userEmail)
                        .orElseThrow(() -> new BadRequestException("No equipment found with ID: " + equipmentId));

                if (!"equipment".equalsIgnoreCase(equipment.getType()))
                    throw new BadRequestException("Object " + equipmentId + " is not of type 'equipment'");

                // Ensure equipment is currently FREE
                if (equipment.getStatus() != ObjectStatus.FREE) {
                    throw new BadRequestException(
                            "Equipment " + equipmentId + " is not available (status: " + equipment.getStatus() + ")");
                }

                // Mark equipment as "taken"
                equipment.setStatus(ObjectStatus.TAKEN); // Assuming TAKEN is defined in your ObjectStatus enum
                this.objectLogic.update(equipmentId, equipment, userSystemID, userEmail);

                // Build new activity object
                activity = new ObjectBoundary();
                activity.setId(new ObjectIDBoundary());
                activity.setType("activity");
                activity.setAlias(activityName);
                activity.setStatus(ObjectStatus.DEFAULT);
                activity.setActive(true);
                activity.setCreationTimestamp(new Date());
                activity.setCreatedBy(command.getInvokedBy());
                // Add initial details
                Map<String, Object> activityDetails = new HashMap<>();
                activityDetails.put("reps", 0);
                activityDetails.put("Finished", null);
                activityDetails.put("equipment", equipment);
                activity.setDetails(activityDetails);

                ObjectBoundary createdActivity = this.objectLogic.createNewObject(activity, userSystemID, userEmail);
                this.objectLogic.bindObjects(sessionObject.getId().getObjectId(), createdActivity.getId().getObjectId(),
                        userSystemID, userEmail);

                // Start the TCP listener for this activity
                this.sensorLogic.startSimulation(createdActivity, this.objectLogic);

                returnedObjects.add(createdActivity);

                break;
            case "finish-activity":

                if (command.getTargetObject() == null)
                    throw new BadRequestException("Missing targetObject (activity)");

                activity = this.objectLogic
                        .getSpecificObject(command.getTargetObject().getObjectId(), userSystemID, userEmail)
                        .orElseThrow(() -> new BadRequestException(
                                "No activity found with ID: " + command.getTargetObject().getObjectId()));

                if (!"activity".equalsIgnoreCase(activity.getType()))
                    throw new BadRequestException("Target object must be of type 'activity'");

                // Update "Finished" field to current timestamp
                Map<String, Object> newActivityDetails = activity.getDetails();
                if (newActivityDetails == null) {
                    newActivityDetails = new HashMap<>();
                }
                newActivityDetails.put("Finished", new Date());


                ObjectBoundary equipmentMap = (ObjectBoundary) newActivityDetails.get("equipment");
                if (equipmentMap == null)
                    throw new BadRequestException("Missing 'equipment' in activity details");

                ObjectIDBoundary equipmentIdMap = equipmentMap.getId();
                if (equipmentIdMap == null)
                    throw new BadRequestException("Missing 'id' inside equipment object");

                String activityEquipmentId = equipmentIdMap.getObjectId();
                if (activityEquipmentId == null)
                    throw new BadRequestException("Missing 'objectId' in equipment id");

                // Load the equipment object
                ObjectBoundary activityEquipment = this.objectLogic
                        .getSpecificObject(activityEquipmentId, userSystemID, userEmail)
                        .orElseThrow(() -> new BadRequestException("No equipment found with ID: " + activityEquipmentId));

                if (!"equipment".equalsIgnoreCase(activityEquipment.getType()))
                    throw new BadRequestException("Referenced object " + activityEquipmentId + " is not of type 'equipment'");

                activityEquipment.setStatus(ObjectStatus.FREE);
                this.objectLogic.update(activityEquipmentId, activityEquipment, userSystemID, userEmail);

                newActivityDetails.put("equipment", activityEquipment);

                activity.setDetails(newActivityDetails);

                this.objectLogic.update(
                        activity.getId().getObjectId(), activity, userSystemID, userEmail);

                activity = this.objectLogic
                        .getSpecificObject(activity.getId().getObjectId(), userSystemID, userEmail)
                        .get();

                this.sensorLogic.stopSimulation();

                returnedObjects.add(activity);


                break;
            default:
                // fallback logic
                throw new BadRequestException("Unknown command: " + command.getCommand());
        }

        return returnedObjects;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAllCommands(String userSystemID, String userEmail) throws ForbiddenException {
        boolean auth = this.userLogic.authAdmin(userSystemID, userEmail);
        if (!auth)
            throw new ForbiddenException("Only ADMIN can delete all Commands");
        this.commandCrud.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandBoundary> getAllCommands(String userSystemID, String userEmail, int size, int page)
            throws ForbiddenException {
        boolean auth = this.userLogic.authAdmin(userSystemID, userEmail);
        if (!auth)
            throw new ForbiddenException("Only ADMIN can get all Commands");
        return this.commandCrud.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "commandID")).getContent()
                .stream().map(this.commandConverter::fromEntity).toList();
    }
}
