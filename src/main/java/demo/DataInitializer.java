package demo;

import demo.BusinessLogic.CommandLogic;
import demo.BusinessLogic.ObjectLogicWithRelations;
import demo.BusinessLogic.UserLogic;
import demo.Controller.ObjectBoundary;
import demo.Controller.UserBoundary;
import demo.Controller.UserIDBoundary;
import demo.Exception.*;
import demo.Repository.ObjectStatus;
import demo.Repository.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserLogic userLogic;
    private final CommandLogic commandLogic;
    private final ObjectLogicWithRelations objectLogic;

    public DataInitializer(UserLogic userLogic, CommandLogic commandLogic, ObjectLogicWithRelations objectLogic) {
        this.userLogic = userLogic;
        this.commandLogic = commandLogic;
        this.objectLogic = objectLogic;
    }

    @Override
    public void run(String... args) throws RuntimeException {

        // ADMIN user to delete all data
        UserBoundary adminToDelete = new UserBoundary();
        adminToDelete.setUserId(new UserIDBoundary("delete", "2025b.garzon.yuval"));
        adminToDelete.setUserName("adminToDelete");
        adminToDelete.setRole(UserRole.ADMIN);
        adminToDelete.setAvatar("👑");

        try {
            adminToDelete = userLogic.createUser(adminToDelete);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        // Delete all database
        try {
            objectLogic.deleteAllObjects(adminToDelete.getUserId().getSystemID(), adminToDelete.getUserId().getEmail());
            commandLogic.deleteAllCommands(adminToDelete.getUserId().getSystemID(), adminToDelete.getUserId().getEmail());
            userLogic.deleteAllUsers(adminToDelete.getUserId().getSystemID(), adminToDelete.getUserId().getEmail());
        } catch (ForbiddenException e) {
            throw new RuntimeException(e);
        }

        // ADMIN user
        UserBoundary admin = new UserBoundary();
        admin.setUserId(new UserIDBoundary("admin@admin.com"));
        admin.setUserName("Admin_User");
        admin.setRole(UserRole.ADMIN);
        admin.setAvatar("👑");

        try {
            admin = userLogic.createUser(admin);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        // OPERATOR user
        UserBoundary operator = new UserBoundary();
        operator.setUserId(new UserIDBoundary("operator@operator.com"));
        operator.setUserName("Operator_User");
        operator.setRole(UserRole.OPERATOR);
        operator.setAvatar("🛠️");

        try {
            operator = userLogic.createUser(operator);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        // END_USER
        UserBoundary END_USER = new UserBoundary();
        END_USER.setUserId(new UserIDBoundary("endUser@endUser.com"));
        END_USER.setUserName("END_USER");
        END_USER.setRole(UserRole.END_USER);
        END_USER.setAvatar("🙂");

        try {
            END_USER = userLogic.createUser(END_USER);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }

        // Trainee 1
        ObjectBoundary trainee1 = new ObjectBoundary();
        trainee1.setCreatedBy(admin.getUserId());
        trainee1.setType("trainee");
        trainee1.setAlias("John Doe");
        trainee1.setStatus(ObjectStatus.DEFAULT);
        trainee1.setActive(true);
        trainee1.setCreationTimestamp(new Date());
        // Set trainee details
        Map<String, Object> details1 = new HashMap<>();
        details1.put("age", 28);
        details1.put("gender", "male");
        details1.put("heightCm", 180);
        details1.put("weightKg", 75);
        trainee1.setDetails(details1);
        trainee1 = objectLogic.createNewObject(trainee1, admin.getUserId().getSystemID(), admin.getUserId().getEmail());

        // Trainee 2
        ObjectBoundary trainee2 = new ObjectBoundary();
        trainee2.setCreatedBy(operator.getUserId());
        trainee2.setType("trainee");
        trainee2.setAlias("Jane Smith");
        trainee2.setStatus(ObjectStatus.DEFAULT);
        trainee2.setActive(true);
        trainee2.setCreationTimestamp(new Date());
        // Set trainee details
        Map<String, Object> details2 = new HashMap<>();
        details2.put("age", 25);
        details2.put("gender", "female");
        details2.put("heightCm", 165);
        details2.put("weightKg", 60);
        trainee2.setDetails(details2);
        trainee2 = objectLogic.createNewObject(trainee2, admin.getUserId().getSystemID(), admin.getUserId().getEmail());

        // Trainee 3
        ObjectBoundary trainee3 = new ObjectBoundary();
        trainee3.setCreatedBy(END_USER.getUserId());
        trainee3.setType("trainee");
        trainee3.setAlias("Mike Johnson");
        trainee3.setStatus(ObjectStatus.DEFAULT);
        trainee3.setActive(true);
        trainee3.setCreationTimestamp(new Date());
        // Set trainee details
        Map<String, Object> details3 = new HashMap<>();
        details3.put("age", 30);
        details3.put("gender", "male");
        details3.put("heightCm", 175);
        details3.put("weightKg", 80);
        trainee3.setDetails(details3);
        trainee3 = objectLogic.createNewObject(trainee3, admin.getUserId().getSystemID(), admin.getUserId().getEmail());

        // Create object session
        ObjectBoundary session = new ObjectBoundary();
        session.setCreatedBy(admin.getUserId());
        session.setType("session");
        session.setAlias("Training Session");
        session.setStatus(ObjectStatus.DEFAULT);
        session.setActive(true);
        session.setCreationTimestamp(new Date());
        // Set session details
        Map<String, Object> sessionDetails = new HashMap<>();
        Date endSessionTime = new Date();
        // 2 hours later
        endSessionTime.setTime(endSessionTime.getTime() + 2 * 60 * 60 * 1000);
        sessionDetails.put("Finished", endSessionTime);
        session.setDetails(sessionDetails);
        session = objectLogic.createNewObject(session, admin.getUserId().getSystemID(), admin.getUserId().getEmail());

        // Bind Admin trainee to the session
        objectLogic.bindObjects(trainee1.getId().getObjectId(), session.getId().getObjectId(), admin.getUserId().getSystemID(), admin.getUserId().getEmail());

        // Create objects equipment
        // Dumbbell Equipment
        ObjectBoundary equipment1 = new ObjectBoundary();
        equipment1.setCreatedBy(admin.getUserId());
        equipment1.setType("equipment");
        equipment1.setAlias("Dumbbell");
        equipment1.setStatus(ObjectStatus.FREE);
        equipment1.setActive(true);
        equipment1.setCreationTimestamp(new Date());
        // Set equipment details
        Map<String, Object> equipmentDetails1 = new HashMap<>();
        equipmentDetails1.put("WeightKg", 20);
        equipmentDetails1.put("Location", "Rack A");
        equipmentDetails1.put("manufacturer", "GymPro");
        String[] activitiesSupported = {"Biceps Curl", "Squat"};
        equipmentDetails1.put("activitiesSupported", activitiesSupported);
        equipment1.setDetails(equipmentDetails1);
        equipment1 = objectLogic.createNewObject(equipment1, admin.getUserId().getSystemID(), admin.getUserId().getEmail());

        // Bench Press Equipment
        ObjectBoundary equipment2 = new ObjectBoundary();
        equipment2.setCreatedBy(admin.getUserId());
        equipment2.setType("equipment");
        equipment2.setAlias("Bench Press");
        equipment2.setStatus(ObjectStatus.FREE);
        equipment2.setActive(true);
        equipment2.setCreationTimestamp(new Date());
        // Set equipment details
        Map<String, Object> equipmentDetails2 = new HashMap<>();
        equipmentDetails2.put("WeightKg", 60);
        equipmentDetails2.put("Location", "Station B");
        equipmentDetails2.put("manufacturer", "StrongLift");
        String[] benchActivities = {"Bench Press"};
        equipmentDetails2.put("benchActivities", benchActivities);
        equipment2.setDetails(equipmentDetails2);
        equipment2 = objectLogic.createNewObject(equipment2, admin.getUserId().getSystemID(), admin.getUserId().getEmail());

        // Deadlift Equipment
        ObjectBoundary equipment3 = new ObjectBoundary();
        equipment3.setCreatedBy(admin.getUserId());
        equipment3.setType("equipment");
        equipment3.setAlias("Deadlift");
        equipment3.setStatus(ObjectStatus.FREE);
        equipment3.setActive(true);
        equipment3.setCreationTimestamp(new Date());
        // Set equipment details
        Map<String, Object> equipmentDetails3 = new HashMap<>();
        equipmentDetails3.put("WeightKg", 100);
        equipmentDetails3.put("Location", "Platform C");
        equipmentDetails3.put("manufacturer", "IronCore");
        String[] deadliftActivities = {"Deadlift", "Barbell Row"};
        equipmentDetails3.put("deadliftActivities", deadliftActivities);
        equipment3.setDetails(equipmentDetails3);
        equipment3 = objectLogic.createNewObject(equipment3, admin.getUserId().getSystemID(), admin.getUserId().getEmail());

        // Create objects activity
        // activity1: Biceps Curl
        ObjectBoundary activity1 = new ObjectBoundary();
        activity1.setCreatedBy(admin.getUserId());
        activity1.setType("activity");
        activity1.setAlias("Biceps Curl");
        activity1.setStatus(ObjectStatus.DEFAULT);
        activity1.setActive(true);
        activity1.setCreationTimestamp(new Date());
        // Set activity details
        Map<String, Object> activityDetails1 = new HashMap<>();
        Date endActivityTime = new Date();
        // 2 hours later
        endActivityTime.setTime(endActivityTime.getTime() + 2 * 60 * 60 * 1000);
        activityDetails1.put("Finished", endActivityTime);
        activityDetails1.put("reps", 10);
        activityDetails1.put("equipment", equipment1);
        activity1.setDetails(activityDetails1);
        activity1 = objectLogic.createNewObject(activity1, admin.getUserId().getSystemID(), admin.getUserId().getEmail());

        // activity2: Squat
        ObjectBoundary activity2 = new ObjectBoundary();
        activity2.setCreatedBy(admin.getUserId());
        activity2.setType("activity");
        activity2.setAlias("Squat");
        activity2.setStatus(ObjectStatus.DEFAULT);
        activity2.setActive(true);
        activity2.setCreationTimestamp(new Date());
        // Set activity details
        Map<String, Object> activityDetails2 = new HashMap<>();
        activityDetails2.put("Finished", endActivityTime);
        activityDetails2.put("reps", 8);
        activityDetails2.put("equipment", equipment2);
        activity2.setDetails(activityDetails2);
        activity2 = objectLogic.createNewObject(activity2, admin.getUserId().getSystemID(), admin.getUserId().getEmail());

        // activity3: Bench Press
        ObjectBoundary activity3 = new ObjectBoundary();
        activity3.setCreatedBy(admin.getUserId());
        activity3.setType("activity");
        activity3.setAlias("Bench Press");
        activity3.setStatus(ObjectStatus.DEFAULT);
        activity3.setActive(true);
        activity3.setCreationTimestamp(new Date());
        // Set activity details
        Map<String, Object> activityDetails3 = new HashMap<>();
        activityDetails3.put("Finished", endActivityTime);
        activityDetails3.put("reps", 10);
        activityDetails3.put("equipment", equipment3);
        activity3.setDetails(activityDetails3);
        activity3 = objectLogic.createNewObject(activity3, admin.getUserId().getSystemID(), admin.getUserId().getEmail());

        // Bind activities to the session
        objectLogic.bindObjects(session.getId().getObjectId(), activity1.getId().getObjectId(), admin.getUserId().getSystemID(), admin.getUserId().getEmail());
        objectLogic.bindObjects(session.getId().getObjectId(), activity2.getId().getObjectId(), admin.getUserId().getSystemID(), admin.getUserId().getEmail());
        objectLogic.bindObjects(session.getId().getObjectId(), activity3.getId().getObjectId(), admin.getUserId().getSystemID(), admin.getUserId().getEmail());

    }
}