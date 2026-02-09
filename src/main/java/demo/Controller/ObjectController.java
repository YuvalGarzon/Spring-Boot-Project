package demo.Controller;

import demo.Exception.BadRequestException;
import demo.Exception.ForbiddenException;
import demo.Exception.NotFoundException;
import demo.BusinessLogic.ObjectLogicWithPagination;
import demo.Repository.ObjectStatus;
import demo.BusinessLogic.UserLogic;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/ambient-intelligence/objects"})

public class ObjectController {

    private ObjectLogicWithPagination objectLogic;
    private UserLogic userLogic;

    public ObjectController(ObjectLogicWithPagination objectLogic, UserLogic userLogic) {
        this.objectLogic = objectLogic;
        this.userLogic = userLogic;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ObjectBoundary createNewObject (
            @RequestParam(name = "userSystemID", required = false, defaultValue = "none") String userSystemID,
            @RequestParam(name = "userEmail", required = false, defaultValue = "none") String userEmail,
            @RequestBody ObjectBoundary object) throws ForbiddenException, BadRequestException {

        System.err.println("*** createNewObject(" + object + ")");

        object = this.objectLogic.createNewObject(object, userSystemID, userEmail);

        return object;

    }

    @GetMapping(path = {"/search/byType/{type}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ObjectBoundary[] getAllObjectsByType(@PathVariable(name = "type") String type,
                                                @RequestParam(name = "userSystemID", required = false, defaultValue = "none") String userSystemID,
                                                @RequestParam(name = "userEmail", required = false, defaultValue = "none") String userEmail,
                                                @RequestParam(name = "size", required = false, defaultValue = "15") int size,
                                                @RequestParam(name = "page", required = false, defaultValue = "0") int page) throws ForbiddenException {

        System.err.println("getAllObjectsByType");

        return this.objectLogic.getAllByType(type, userSystemID, userEmail, size, page).toArray(new ObjectBoundary[0]);
    }

    @GetMapping(path = {"/search/byAlias/{alias}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ObjectBoundary[] getAllObjectsByAlias(@PathVariable(name = "alias") String alias,
                                                 @RequestParam(name = "userSystemID", required = false, defaultValue = "none") String userSystemID,
                                                 @RequestParam(name = "userEmail", required = false, defaultValue = "none") String userEmail,
                                                 @RequestParam(name = "size", required = false, defaultValue = "15") int size,
                                                 @RequestParam(name = "page", required = false, defaultValue = "0") int page) throws ForbiddenException {

        System.err.println("getAllObjectsByAlias");

        return this.objectLogic.getAllByAlias(alias, userSystemID, userEmail, size, page).toArray(new ObjectBoundary[0]);
    }

    @GetMapping(path = {"/search/byStatus/{status}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ObjectBoundary[] getAllObjectsByStatus(@PathVariable(name = "status") String status,
                                                  @RequestParam(name = "userSystemID", required = false, defaultValue = "none") String userSystemID,
                                                  @RequestParam(name = "userEmail", required = false, defaultValue = "none") String userEmail,
                                                  @RequestParam(name = "size", required = false, defaultValue = "15") int size,
                                                  @RequestParam(name = "page", required = false, defaultValue = "0") int page) throws ForbiddenException {

        System.err.println("getAllObjectsByStatus");

        return this.objectLogic.getAllByStatus(ObjectStatus.valueOf(status), userSystemID, userEmail, size, page).toArray(new ObjectBoundary[0]);
    }

    @GetMapping(path = {"/search/byTypeAndStatus/{type}/{status}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ObjectBoundary[] getAllObjectsByTypeAndStatus(@PathVariable(name = "type") String type,
                                                         @PathVariable(name = "status") String status,
                                                         @RequestParam(name = "userSystemID", required = false, defaultValue = "none") String userSystemID,
                                                         @RequestParam(name = "userEmail", required = false, defaultValue = "none") String userEmail,
                                                         @RequestParam(name = "size", required = false, defaultValue = "15") int size,
                                                         @RequestParam(name = "page", required = false, defaultValue = "0") int page) throws ForbiddenException {

        System.err.println("getAllObjectsByTypeAndStatus");

        return this.objectLogic.getAllByTypeAndStatus(type, ObjectStatus.valueOf(status), userSystemID, userEmail, size, page)
                .toArray(new ObjectBoundary[0]);
    }


    @GetMapping(path = {"/search/byAliasPattern/{pattern}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ObjectBoundary[] getAllObjectsByTypeAndStatus(@PathVariable(name = "pattern") String pattern,
                                                         @RequestParam(name = "userSystemID", required = false, defaultValue = "none") String userSystemID,
                                                         @RequestParam(name = "userEmail", required = false, defaultValue = "none") String userEmail,
                                                         @RequestParam(name = "size", required = false, defaultValue = "15") int size,
                                                         @RequestParam(name = "page", required = false, defaultValue = "0") int page) throws ForbiddenException {

        System.err.println("getAllObjectsByTypeAndStatus");

        return this.objectLogic.getAllByAliasPattern(pattern, userSystemID, userEmail, size, page)
                .toArray(new ObjectBoundary[0]);
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ObjectBoundary[] getAllObjectsFromDatabase( @RequestParam(name = "userSystemID", required = false, defaultValue = "none") String userSystemID,
            @RequestParam(name = "userEmail", required = false, defaultValue = "none") String userEmail,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page) throws ForbiddenException {

        System.err.println("getAllObjectsFromDatabase");

        return this.objectLogic.getAll(userSystemID, userEmail, size, page).toArray(new ObjectBoundary[0]);
    }

    @GetMapping(path = {"/{systemID}/{objectId}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ObjectBoundary getSpecificObject(@PathVariable("systemID") String systemID,
                                            @PathVariable("objectId") String objectId,
                                            @RequestParam(name = "userSystemID", required = false, defaultValue = "none") String userSystemID,
                                            @RequestParam(name = "userEmail", required = false, defaultValue = "none") String userEmail) throws NotFoundException, ForbiddenException {

        System.err.println("*** getSpecificObject(" + systemID + ", " + objectId + ")");

        return this.objectLogic.getSpecificObject(objectId, userSystemID, userEmail)
                .orElseThrow(() -> new NotFoundException("Could not find object by id: " + objectId));

    }

    @PutMapping(path = {"/{systemID}/{objectId}"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateSepecificObject(@PathVariable("systemID") String systemID,
                                      @PathVariable("objectId") String objectId,
                                      @RequestParam(name = "userSystemID", required = false, defaultValue = "none") String userSystemID,
                                      @RequestParam(name = "userEmail", required = false, defaultValue = "none") String userEmail,
                                      @RequestBody ObjectBoundary boundaryForUpdate) throws ForbiddenException {

        System.err.println("*** updateSepecificObject(" + systemID + "," + objectId + ", " + boundaryForUpdate + ")");
        this.objectLogic.update(objectId, boundaryForUpdate, userSystemID, userEmail);

    }

    @PutMapping(path = {"/{parentSystemID}/{parentObjectId}/children"}, consumes = {
            MediaType.APPLICATION_JSON_VALUE})
    public void bindObjects(@PathVariable("parentSystemID") String parentSystemId,
                            @PathVariable("parentObjectId") String parentObjectId,
                            @RequestBody ChildIdBoundary childId,
                            @RequestParam(name = "userSystemID", required = false, defaultValue = "none") String userSystemID,
                            @RequestParam(name = "userEmail", required = false, defaultValue = "none") String userEmail) throws ForbiddenException {
        this.objectLogic.bindObjects(parentObjectId, childId.getObjectId(), userSystemID, userEmail);
    }

    @GetMapping(path = {"/{childSystemID}/{childObjectId}/parents"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ObjectBoundary getOrigin(@PathVariable("childSystemID") String childSystemID,
                                    @PathVariable("childObjectId") String childObjectId,@RequestParam(name = "userSystemID", required = false, defaultValue = "none") String userSystemID,
                                    @RequestParam(name = "userEmail", required = false, defaultValue = "none") String userEmail) {
        return this.objectLogic.getParent(childObjectId, userSystemID,userEmail).orElseThrow(
                () -> new NotFoundException("Could not find parent for message with id: " + childObjectId));
    }

    @GetMapping(path = {"/{parentSystemID}/{parentObjectID}/children"}, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ObjectBoundary[] getAllObjectsByParent(@PathVariable(name = "parentSystemID") String parentSystemID,
                                                  @PathVariable(name = "parentObjectID") String parentObjectID,
                                                  @RequestParam(name = "userSystemID", required = false, defaultValue = "none") String userSystemID,
                                                  @RequestParam(name = "userEmail", required = false, defaultValue = "none") String userEmail,
                                                  @RequestParam(name = "size", required = false, defaultValue = "15") int size,
                                                  @RequestParam(name = "page", required = false, defaultValue = "0") int page) throws ForbiddenException {

        System.err.println("getAllObjectsByParent");

        return this.objectLogic.getChildren(userSystemID, userEmail, parentSystemID, parentObjectID, size, page).toArray(new ObjectBoundary[0]);
    }

}
