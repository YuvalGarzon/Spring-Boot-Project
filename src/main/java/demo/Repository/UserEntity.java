package demo.Repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "USERS")
public class UserEntity {
    @Id
    private String userEmail;
    private String systemId;
    private UserRole role;
    private String username;
    private String avatar;

    public UserEntity() {
    }

    public UserEntity(String userEmail, String systemId, UserRole role, String username, String avatar) {
        this.userEmail = userEmail;
        this.systemId = systemId;
        this.role = role;
        this.username = username;
        this.avatar = avatar;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
