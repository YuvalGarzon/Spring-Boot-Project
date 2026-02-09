package demo.BusinessLogic;

import demo.Controller.UserBoundary;
import demo.Repository.UserEntity;
import demo.Controller.UserIDBoundary;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserEntity toEntity(UserBoundary boundary) {
        if (boundary == null || boundary.getUserId() == null) {
            throw new RuntimeException("Invalid UserBoundary");
        }

        UserEntity entity = new UserEntity();
        entity.setUserEmail(boundary.getUserId().getEmail());
        entity.setSystemId(boundary.getUserId().getSystemID());
        entity.setUsername(boundary.getUserName());
        entity.setRole(boundary.getRole());
        entity.setAvatar(boundary.getAvatar());
        return entity;
    }

    public UserBoundary fromEntity(UserEntity entity) {
        if (entity == null) {
            throw new RuntimeException("UserEntity is null");
        }

        UserIDBoundary userId = new UserIDBoundary();
        userId.setEmail(entity.getUserEmail());
        userId.setSystemID(entity.getSystemId());
        UserBoundary boundary = new UserBoundary();
        boundary.setUserId(userId);
        boundary.setUserName(entity.getUsername());
        boundary.setRole(entity.getRole());
        boundary.setAvatar(entity.getAvatar());
        return boundary;
    }
}
