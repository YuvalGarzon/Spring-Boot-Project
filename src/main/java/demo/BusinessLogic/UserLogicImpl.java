package demo.BusinessLogic;

import java.util.List;
import java.util.Optional;

import demo.Controller.UserBoundary;
import demo.Exception.*;
import demo.Repository.UserCrud;
import demo.Repository.UserEntity;
import demo.Repository.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import demo.Exception.NotFoundException;

@Service
public class UserLogicImpl implements UserLogic {

    private final UserCrud userCrud;
    private final UserConverter converter;
    private String appName;

    public UserLogicImpl(UserCrud userCrud, UserConverter converter) {
        this.userCrud = userCrud;
        this.converter = converter;
    }

    @Value("${spring.application.name:defaultApp}")
    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    @Transactional
    public UserBoundary createUser(UserBoundary boundary) throws BadRequestException {
        if (boundary == null || boundary.getUserId() == null || boundary.getUserId().getEmail().trim().isEmpty())
            throw new BadRequestException("Invalid user");

        if (boundary.getUserName() == null || boundary.getUserName().trim().isEmpty())
            throw new BadRequestException("Username cannot be null or empty");

        if (boundary.getAvatar() == null || boundary.getAvatar().trim().isEmpty())
            throw new BadRequestException("Avatar cannot be null or empty");

        UserEntity entity = this.converter.toEntity(boundary);
        entity.setSystemId(appName);
        this.userCrud.save(entity);
        return this.converter.fromEntity(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserBoundary> getUserByEmail(String userEmail) {
        Optional<UserEntity> entity = this.userCrud.findByUserEmail(userEmail);
        return entity.map(this.converter::fromEntity);
    }

    @Override
    public Optional<UserBoundary> getUserByEmailAndSystemId(String userEmail, String systemId) throws BadRequestException {
        Optional<UserEntity> entity = this.userCrud.findByUserEmailAndSystemId(userEmail, systemId);
        return entity.map(this.converter::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserBoundary> getAllUsers(String userSystemID, String userEmail, int size, int page) throws ForbiddenException {
        if (!authAdmin(userSystemID, userEmail))
            throw new ForbiddenException("Only ADMIN can get all Users");
        return this.userCrud.findAll(PageRequest.of(page, size, Sort.Direction.DESC, "email")).getContent().stream()
                .map(this.converter::fromEntity).toList();
    }

    @Override
    @Transactional
    public void deleteAllUsers(String userSystemID, String userEmail) throws ForbiddenException {
        if (!authAdmin(userSystemID, userEmail))
            throw new ForbiddenException("Only ADMIN can delete all Users");
        this.userCrud.deleteAll();
    }

    @Override
    @Transactional
    public void updateUser(String systemID, String userEmail, UserBoundary update) {
        UserEntity existing = this.userCrud.findById(userEmail)
                .orElseThrow(() -> new NotFoundException("User with email " + userEmail + " not found"));

        if (!existing.getSystemId().equals(systemID))
            throw new ForbiddenException("SystemID mismatch");

        if (update.getUserName() != null)
            existing.setUsername(update.getUserName());

        if (update.getAvatar() != null)
            existing.setAvatar(update.getAvatar());

        if (update.getRole() != null)
            existing.setRole(UserRole.valueOf(update.getRole().name()));

        this.userCrud.save(existing);
    }

    public boolean authAdmin(String systemId, String userEmail) throws BadRequestException {
        UserBoundary user = this.getUserByEmailAndSystemId(userEmail, systemId).orElseThrow(() -> new BadRequestException("There is no user in system with this email : " + userEmail + " and systemId : " + systemId));

        if (user != null) {
            if (user.getRole().equals(UserRole.ADMIN))
                return true;
        }
        return false;

    }

    public boolean authOperator(String systemId, String userEmail) throws BadRequestException {
        UserBoundary user = this.getUserByEmailAndSystemId(userEmail, systemId).orElseThrow(() -> new BadRequestException("There is no user in system with this email : " + userEmail + " and systemId : " + systemId));


        if (user != null) {
            if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.OPERATOR))
                return true;

        }
        return false;
    }

    public boolean authEndUser(String systemId, String userEmail) throws BadRequestException {
        UserBoundary user = this.getUserByEmailAndSystemId(userEmail, systemId).orElseThrow(() -> new BadRequestException("There is no user in system with this email : " + userEmail + " and systemId : " + systemId));

        if (user != null) {
            if (user.getRole().equals(UserRole.ADMIN) || user.getRole().equals(UserRole.OPERATOR)
                    || user.getRole().equals(UserRole.END_USER))
                return true;

        }
        return false;
    }

}
