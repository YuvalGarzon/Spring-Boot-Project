package demo.Controller;

import demo.Exception.ForbiddenException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import demo.BusinessLogic.CommandLogic;
import demo.BusinessLogic.ObjectLogic;
import demo.BusinessLogic.UserLogic;


@RestController
@RequestMapping(path = {"/ambient-intelligence/admin"})


public class AdminController {
    private CommandLogic commandLogic;
    private ObjectLogic objectLogic;
    private UserLogic userLogic;

    public AdminController(CommandLogic commandLogic, ObjectLogic objectLogic, UserLogic userLogic) {
        this.commandLogic = commandLogic;
        this.objectLogic = objectLogic;
        this.userLogic = userLogic;
    }

    @GetMapping(path = {"users"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserBoundary[] getAllUsers(@RequestParam(name = "userSystemID") String userSystemID,
                                      @RequestParam(name = "userEmail") String userEmail,
                                      @RequestParam(name = "size", defaultValue = "15") int size,
                                      @RequestParam(name = "page", defaultValue = "0") int page) throws ForbiddenException {
        System.err.println("*** getAllUsers()");
        return this.userLogic.getAllUsers(userSystemID, userEmail, size, page).toArray(new UserBoundary[0]);
    }

    @GetMapping(path = {"commands"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public CommandBoundary[] getCommands(@RequestParam(name = "userSystemID") String userSystemID,
                                         @RequestParam(name = "userEmail") String userEmail,
                                         @RequestParam(name = "size", defaultValue = "15") int size,
                                         @RequestParam(name = "page", defaultValue = "0") int page) throws ForbiddenException {
        System.err.println("*** getAllCommands()");
        return this.commandLogic.getAllCommands(userSystemID, userEmail, size, page).toArray(new CommandBoundary[0]);
    }

    @DeleteMapping(path = {"objects"})
    public void deleteAllObjects(@RequestParam(name = "userSystemID") String userSystemID,
                                 @RequestParam(name = "userEmail") String userEmail) throws ForbiddenException {
        System.err.println("*** deleteAllObjects()");
        this.objectLogic.deleteAllObjects(userSystemID, userEmail);
    }

    @DeleteMapping(path = {"commands"})
    public void deleteAllCommands(@RequestParam(name = "userSystemID") String userSystemID,
                                  @RequestParam(name = "userEmail") String userEmail) throws ForbiddenException {
        System.err.println("*** deleteCommand()");
        this.commandLogic.deleteAllCommands(userSystemID, userEmail);
    }

    @DeleteMapping(path = {"users"})
    public void deleteAllUsers(@RequestParam(name = "userSystemID") String userSystemID,
                               @RequestParam(name = "userEmail") String userEmail) throws ForbiddenException {
        System.err.println("*** deleteAllUsers() - userSystemID: " + userSystemID + ", userEmail: " + userEmail);
        this.userLogic.deleteAllUsers(userSystemID, userEmail);
    }

}
