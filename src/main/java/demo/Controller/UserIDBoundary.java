package demo.Controller;

import demo.Repository.UserIDEntity;

public class UserIDBoundary {

    private String email;

    private String systemID;


    public UserIDBoundary() {
    }

    public UserIDBoundary(String email, String systemId) {
        this.email = email;
        this.systemID = systemId;
    }

    public UserIDBoundary(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSystemID() {
        return systemID;
    }

    public void setSystemID(String systemId) {
        this.systemID = systemId;
    }

    @Override
    public String toString() {
        return "UserID [email=" + email + ", systemID=" + systemID + "]";
    }

    public UserIDEntity toEntity() {

        UserIDEntity rv = new UserIDEntity(this.email);

        return rv;

    }

}
