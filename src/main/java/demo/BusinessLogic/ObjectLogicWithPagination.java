package demo.BusinessLogic;

import java.util.List;

import demo.Exception.ForbiddenException;
import demo.Controller.ObjectBoundary;
import demo.Repository.ObjectStatus;

public interface ObjectLogicWithPagination extends ObjectLogicWithRelations {

    public List<ObjectBoundary> getAll(String userSystemId, String userEmail, int size, int page) throws ForbiddenException;

    public List<ObjectBoundary> getAllByType(String type, String userSystemID, String userEmail, int size, int page) throws ForbiddenException;

    public List<ObjectBoundary> getAllByAlias(String alias, String userSystemID, String userEmail, int size, int page) throws ForbiddenException;

    public List<ObjectBoundary> getAllByAliasPattern(String aliasPattern, String userSystemID, String userEmail, int size, int page) throws ForbiddenException;

    public List<ObjectBoundary> getAllByStatus(ObjectStatus status, String userSystemID, String userEmail, int size, int page) throws ForbiddenException;

    public List<ObjectBoundary> getAllByTypeAndStatus(String type, ObjectStatus status, String userSystemID, String userEmail, int size, int page) throws ForbiddenException;

    public List<ObjectBoundary> getChildren(String userSystemId, String userEmail, String parentSystemId, String parentId, int size, int page) throws ForbiddenException;
}
