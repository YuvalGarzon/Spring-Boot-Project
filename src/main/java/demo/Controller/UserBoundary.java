package demo.Controller;

import demo.Repository.UserRole;

public class UserBoundary {

    private UserIDBoundary userId;
    private UserRole role;
    private String userName;
    private String avatar;

    public UserBoundary() {
    }

    public UserBoundary(UserIDBoundary userId, UserRole role, String userName, String avatar) {
        this.userId = userId;
        this.role = role;
        this.userName = userName;
        this.avatar = avatar;
    }

    public UserIDBoundary getUserId() {
        return userId;
    }

    public void setUserId(UserIDBoundary userId) {
        this.userId = userId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserBoundary{" +
                "userId=" + userId +
                ", role='" + role + '\'' +
                ", userName='" + userName + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
