package demo.BusinessLogic;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import demo.Controller.ObjectBoundary;
import demo.Controller.ObjectIDBoundary;
import demo.Exception.BadRequestException;
import demo.Exception.ForbiddenException;
import demo.Exception.NotFoundException;
import demo.Helper.ObjectHelper;
import demo.Repository.ObjectCrud;
import demo.Repository.ObjectEntity;

import demo.Repository.ObjectStatus;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class ObjectLogicImpl implements ObjectLogicWithPagination {
    private ObjectCrud objectCrud;
    private ObjectConverter ObjectConverter;
    private String appName;
    private UserLogic userLogic;
    private ObjectHelper helper;

    public ObjectLogicImpl(ObjectCrud objectCrud, ObjectConverter ObjectConverter, UserLogic userLogic,
                           ObjectHelper helper) {
        this.objectCrud = objectCrud;
        this.ObjectConverter = ObjectConverter;
        this.userLogic = userLogic;
        this.helper = helper;
    }

    @Value("${spring.application.name:defaultName}")
    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    @Transactional(readOnly = false)
    public ObjectBoundary createNewObject(ObjectBoundary object, String userSystemId, String email)
            throws ForbiddenException, BadRequestException {

        boolean auth = this.userLogic.authOperator(userSystemId, email);

        if (auth) {
            if (object.getAlias() == null || object.getAlias().trim().isEmpty())
                throw new BadRequestException("Alias cannot be null or empty");

            if (object.getType() == null || object.getType().trim().isEmpty())
                throw new BadRequestException("Type cannot be null or empty");

            if (object.getStatus() == null || object.getStatus().toString().trim().isEmpty())
                throw new BadRequestException("Status cannot be null or empty");

            object.setId(new ObjectIDBoundary("temp", "temp"));
            ObjectEntity entity = this.ObjectConverter.toEntity(object);
            // generate unique new id for message
            entity.setObjectId(UUID.randomUUID().toString());
            entity.setSystemID(appName);
            entity.setCreationTimestamp(new Date());
            entity = this.objectCrud.save(entity);
            return this.ObjectConverter.fromEntity(entity);

        } else {
            throw new ForbiddenException("You do not have the authority to change objects!!!!");
        }

    }

    @Override
//	@Transactional(readOnly = true)
    @Deprecated
    public List<ObjectBoundary> getAll() throws BadRequestException {
        throw new BadRequestException("this method is deprecated, please use getAll(int size, int page)");
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObjectBoundary> getAll(String userSystemId, String userEmail, int size, int page) throws ForbiddenException {
        boolean endUser = this.userLogic.authEndUser(userSystemId, userEmail);
        boolean aboveEndUser = this.userLogic.authOperator(userSystemId, userEmail);

        if (endUser) {

            if (aboveEndUser)
                return this.objectCrud.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId")).stream()
                        .map(this.ObjectConverter::fromEntity).toList();
            else {
                return this.objectCrud.findAllByActiveTrue(PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId")).stream()
                        .map(this.ObjectConverter::fromEntity).toList();
            }
        } else
            throw new ForbiddenException("Must be valid user in system to get objects");
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObjectBoundary> getAllByType(String type, String userSystemID, String userEmail, int size, int page) throws ForbiddenException {

        boolean endUser = this.userLogic.authEndUser(userSystemID, userEmail);
        boolean aboveEndUser = this.userLogic.authOperator(userSystemID, userEmail);

        if (endUser) {

            if (aboveEndUser)
                return this.objectCrud
                        .findAllByType(type, PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId")).stream()
                        .map(this.ObjectConverter::fromEntity).toList();
            else {
                return this.objectCrud.findAllByTypeAndActiveTrue(type, PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId")).stream()
                        .map(this.ObjectConverter::fromEntity).toList();
            }
        } else
            throw new ForbiddenException("Must be valid user in system to get objects");


    }

    @Override
    @Transactional(readOnly = true)
    public List<ObjectBoundary> getAllByAlias(String alias, String userSystemID, String userEmail, int size, int page) throws ForbiddenException {


        boolean endUser = this.userLogic.authEndUser(userSystemID, userEmail);
        boolean aboveEndUser = this.userLogic.authOperator(userSystemID, userEmail);

        if (endUser) {

            if (aboveEndUser)
                return this.objectCrud
                        .findAllByAlias(alias, PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId")).stream()
                        .map(this.ObjectConverter::fromEntity).toList();
            else {
                return this.objectCrud.findAllByAliasAndActiveTrue(alias, PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId")).stream()
                        .map(this.ObjectConverter::fromEntity).toList();
            }
        } else
            throw new ForbiddenException("Must be valid user in system to get objects");


    }

    @Override
    @Transactional(readOnly = true)
    public List<ObjectBoundary> getAllByStatus(ObjectStatus status, String userSystemID, String userEmail, int size, int page) throws ForbiddenException {


        boolean endUser = this.userLogic.authEndUser(userSystemID, userEmail);
        boolean aboveEndUser = this.userLogic.authOperator(userSystemID, userEmail);

        if (endUser) {

            if (aboveEndUser)
                return this.objectCrud
                        .findAllByStatus(status, PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId")).stream()
                        .map(this.ObjectConverter::fromEntity).toList();
            else {
                return this.objectCrud.findAllByStatusAndActiveTrue(status, PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId")).stream()
                        .map(this.ObjectConverter::fromEntity).toList();
            }
        } else
            throw new ForbiddenException("Must be valid user in system to get objects");


    }

    @Override
    @Transactional(readOnly = true)
    public List<ObjectBoundary> getAllByTypeAndStatus(String type, ObjectStatus status, String userSystemID, String userEmail, int size, int page) throws ForbiddenException {

        boolean endUser = this.userLogic.authEndUser(userSystemID, userEmail);
        boolean aboveEndUser = this.userLogic.authOperator(userSystemID, userEmail);

        if (endUser) {

            if (aboveEndUser)
                return this.objectCrud
                        .findAllByTypeAndStatus(type, status, PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId")).stream()
                        .map(this.ObjectConverter::fromEntity).toList();
            else {
                return this.objectCrud.findAllByTypeAndStatusAndActiveTrue(type, status, PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId")).stream()
                        .map(this.ObjectConverter::fromEntity).toList();
            }
        } else
            throw new ForbiddenException("Must be valid user in system to get objects");


    }

    public List<ObjectBoundary> getAllByAliasPattern(String aliasPattern, String userSystemID, String userEmail, int size, int page) throws ForbiddenException {


        boolean endUser = this.userLogic.authEndUser(userSystemID, userEmail);
        boolean aboveEndUser = this.userLogic.authOperator(userSystemID, userEmail);

        if (endUser) {

            if (aboveEndUser)
                return this.objectCrud
                        .findAllByAliasLike("*" + aliasPattern + "*",
                                PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId"))
                        .stream().map(this.ObjectConverter::fromEntity).toList();
            else {
                return this.objectCrud.findAllByAliasLikeAndActiveTrue("*" + aliasPattern + "*",
                                PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId"))
                        .stream().map(this.ObjectConverter::fromEntity).toList();
            }
        } else
            throw new ForbiddenException("Must be valid user in system to get objects");


    }

    @Override
    @Transactional(readOnly = true)
    public List<ObjectBoundary> getChildren(String userSystemID, String userEmail, String parentSystemId, String parentId, int size, int page) throws ForbiddenException {
        boolean endUser = this.userLogic.authEndUser(userSystemID, userEmail);
        boolean aboveEndUser = this.userLogic.authOperator(userSystemID, userEmail);
        if (!endUser)
            throw new ForbiddenException("Must be a valid user in system");


        if (!aboveEndUser) {

            return this.objectCrud
                    .findAllByParent_ObjectIdAndSystemIDAndActiveTrue(parentId, parentSystemId,
                            PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId"))
                    .stream().map(this.ObjectConverter::fromEntity).toList();
        } else return this.objectCrud
                .findAllByParent_ObjectIdAndSystemID(parentId, parentSystemId,
                        PageRequest.of(page, size, Sort.Direction.DESC, "created", "objectId"))
                .stream().map(this.ObjectConverter::fromEntity).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ObjectBoundary> getSpecificObject(String objectId, String userSystemID, String userEmail)
            throws ForbiddenException {
        boolean endUser = this.userLogic.authEndUser(userSystemID, userEmail);
        boolean aboveEndUser = this.userLogic.authOperator(userSystemID, userEmail);
        if (!endUser)
            throw new ForbiddenException("Must be a valid user in system");
        Optional<ObjectEntity> entity = this.objectCrud.findById(objectId);
        if (!aboveEndUser && entity.isPresent() && !entity.get().isActive())
            throw new ForbiddenException("Must be an OPERATOR to get non active object");

        return entity.map(this.ObjectConverter::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ObjectBoundary> getSpecificObjectByTypeAndUserEmail(String type, String userEmail) {
        Optional<ObjectEntity> entity = this.objectCrud.findByTypeAndUserEmail(type, userEmail);
        return entity.map(this.ObjectConverter::fromEntity);
    }


    @Override
    @Transactional(readOnly = false)
    public void deleteAllObjects(String userSystemID, String userEmail) throws ForbiddenException {
        boolean auth = this.userLogic.authAdmin(userSystemID, userEmail);
        if (!auth)
            throw new ForbiddenException("Only ADMIN can delete all Objects");
        this.objectCrud.deleteAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void update(String objectId, ObjectBoundary boundaryForUpdate, String systemId, String email)
            throws ForbiddenException {

        boolean auth = this.userLogic.authOperator(systemId, email);

        if (auth) {

            ObjectEntity existing = this.objectCrud.findById(objectId)
                    .orElseThrow(() -> new NotFoundException("no object to update by id: " + objectId));

            if (boundaryForUpdate.getId().getObjectId() != null) {
                // do nothing
            }

            if (boundaryForUpdate.getCreationTimestamp() != null) {
                // do nothing
            }

            if (boundaryForUpdate.getType() != null) {
                existing.setType(boundaryForUpdate.getType());
            }

            if (boundaryForUpdate.getAlias() != null) {
                existing.setAlias(boundaryForUpdate.getAlias());
            }

            if (boundaryForUpdate.getStatus() != null) {
                existing.setStatus(boundaryForUpdate.getStatus());
            }

            if (boundaryForUpdate.isActive() != null) {
                existing.setActive(boundaryForUpdate.isActive());
            }

            if (boundaryForUpdate.getCreatedBy() != null) {
                // do nothing
            }

            if (boundaryForUpdate.getDetails() != null) {
                existing.setDetails(boundaryForUpdate.getDetails());
            }

            this.objectCrud.save(existing);
        } else {
            throw new ForbiddenException("You do not have the authority to change objects!!!!");
        }

    }

    @Override
    @Transactional(readOnly = false)
    public void bindObjects(String parentId, String childId, String userSystemId, String userEmail) throws ForbiddenException {

        boolean auth = this.userLogic.authOperator(userSystemId, userEmail);

        if (auth) {
            ObjectEntity parent = this.objectCrud.findById(parentId)
                    .orElseThrow(() -> new NotFoundException("could not find parent object by id: " + parentId));
            ObjectEntity child = this.objectCrud.findById(childId)
                    .orElseThrow(() -> new NotFoundException("could not find child message by id: " + childId));
            child.setParent(parent);
            child.setSystemID(parent.getSystemID());
            this.objectCrud.save(child);
        } else
            throw new ForbiddenException("Non Operator cannot bind objects");

    }

    @Override
//	@Transactional(readOnly = true)
    @Deprecated
    public List<ObjectBoundary> getChildren(String parentId) throws BadRequestException {
        throw new BadRequestException(
                "this method is deprecated, please use getChildren(String parentSystemId, String parentId, int size, int page)");

    }

    @Override
    public Optional<ObjectBoundary> getParent(String childId, String userSystemID, String userEmail) throws ForbiddenException {


        ObjectEntity responseEntity = this.objectCrud.findById(childId)
                .orElseThrow(() -> new NotFoundException("Could not find child by id: " + childId));

        boolean endUser = this.userLogic.authEndUser(userSystemID, userEmail);
        boolean aboveEndUser = this.userLogic.authOperator(userSystemID, userEmail);

        if (!endUser)
            throw new ForbiddenException("Must be a valid user in system");

        ObjectEntity origin = responseEntity.getParent();


        if (origin != null) {
            ObjectBoundary rv = this.ObjectConverter.fromEntity(origin);

            if (!aboveEndUser && !rv.isActive())
                throw new ForbiddenException("Must be an OPERATOR to get non active object");

            return Optional.of(rv);
        } else {
            return Optional.empty();
        }


    }
}
