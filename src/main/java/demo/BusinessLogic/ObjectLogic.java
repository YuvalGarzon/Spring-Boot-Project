package demo.BusinessLogic;

import demo.Exception.BadRequestException;
import demo.Exception.ForbiddenException;
import demo.Controller.ObjectBoundary;

import java.util.List;
import java.util.Optional;

public interface ObjectLogic {

    public ObjectBoundary createNewObject(ObjectBoundary object, String userSystemId, String email) throws ForbiddenException, BadRequestException;

    @Deprecated
    public List<ObjectBoundary> getAll() throws BadRequestException;

    public Optional<ObjectBoundary> getSpecificObject(String objectId, String userSystemID, String userEmail) throws ForbiddenException;

    public void deleteAllObjects(String userSystemID, String userEmail) throws ForbiddenException;

    public void update(String objectId, ObjectBoundary boundaryForUpdate, String systemId, String email) throws ForbiddenException;

    public Optional<ObjectBoundary> getSpecificObjectByTypeAndUserEmail(String type, String userEmail);
}
