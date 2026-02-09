package demo.Repository;

import org.springframework.beans.factory.annotation.Value;

public class UserIDEntity {

    private String email;
    @Value("{spring.application.name}")
    private String systemID;

    public UserIDEntity(String email) {
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

    @Override
    public String toString() {
        return "UserIDEntity [email=" + email + ", systemID=" + systemID + "]";
    }

}
