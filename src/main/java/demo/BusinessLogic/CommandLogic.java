package demo.BusinessLogic;

import demo.Controller.CommandBoundary;
import demo.Exception.ForbiddenException;
import demo.Controller.ObjectBoundary;
import demo.Exception.BadRequestException;

import java.util.*;

public interface CommandLogic {

    public List<ObjectBoundary> invokeCommand(CommandBoundary command) throws BadRequestException, ForbiddenException;

    public void deleteAllCommands(String userSystemID, String userEmail) throws ForbiddenException;

    public List<CommandBoundary> getAllCommands(String userSystemID, String userEmail, int size, int page) throws ForbiddenException;

}
