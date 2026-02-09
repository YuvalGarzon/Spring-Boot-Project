package demo.BusinessLogic;

import demo.Exception.ForbiddenException;
import demo.Exception.BadRequestException;
import demo.Controller.UserBoundary;

import java.util.List;
import java.util.Optional;

public interface UserLogic {

    public UserBoundary createUser(UserBoundary user) throws BadRequestException;

    public Optional<UserBoundary> getUserByEmail(String userEmail);

    public Optional<UserBoundary> getUserByEmailAndSystemId(String userEmail, String systemId) throws BadRequestException;

    public List<UserBoundary> getAllUsers(String userSystemID, String userEmail, int size, int page) throws ForbiddenException;

    public void deleteAllUsers(String userSystemID, String userEmail) throws ForbiddenException;

    public void updateUser(String systemID, String userEmail, UserBoundary update);

    public boolean authAdmin(String systemId, String userEmail) throws BadRequestException;

    public boolean authOperator(String systemId, String userEmail) throws BadRequestException;

    public boolean authEndUser(String systemId, String userEmail) throws BadRequestException;

}
