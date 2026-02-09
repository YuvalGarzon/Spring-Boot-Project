package demo.BusinessLogic;

import demo.Exception.BadRequestException;
import demo.Exception.ForbiddenException;
import demo.Controller.ObjectBoundary;

import java.util.List;
import java.util.Optional;

public interface ObjectLogicWithRelations extends ObjectLogic {

    public void bindObjects(String parentId, String childId, String userSystemId, String userEmail) throws ForbiddenException;

    @Deprecated
    public List<ObjectBoundary> getChildren(String parentId) throws BadRequestException;

    public Optional<ObjectBoundary> getParent(String childId, String userSystemID, String userEmail) throws ForbiddenException;
}
