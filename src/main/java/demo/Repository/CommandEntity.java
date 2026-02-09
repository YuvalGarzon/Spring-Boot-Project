package demo.Repository;

import demo.Controller.ObjectIDBoundary;
import demo.Controller.UserIDBoundary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

//@KeySpace("Commands")
@Document(collection = "COMMANDS")
public class CommandEntity {
    @Id
    private String commandID;
    private String systemID;
    private String command;
    private ObjectIDBoundary targetObject;
    private Date invocationTimestamp;
    private UserIDBoundary invokedBy;
    private Map<String, Object> commandAttributes;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Map<String, Object> getCommandAttributes() {
        return commandAttributes;
    }

    public void setCommandAttributes(Map<String, Object> commandAttributes) {
        this.commandAttributes = commandAttributes;
    }

    public Date getInvocationTimestamp() {
        return invocationTimestamp;
    }

    public void setInvocationTimestamp(Date invocationTimestamp) {
        this.invocationTimestamp = invocationTimestamp;
    }

    public UserIDBoundary getInvokedBy() {
        return invokedBy;
    }

    public void setInvokedBy(UserIDBoundary invokedBy) {
        this.invokedBy = invokedBy;
    }

    public ObjectIDBoundary getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(ObjectIDBoundary targetObject) {
        this.targetObject = targetObject;
    }

    public String getCommandID() {
        return commandID;
    }

    public void setCommandID(String commandID) {
        this.commandID = commandID;
    }

    public String getSystemID() {
        return systemID;
    }

    public void setSystemID(String systemID) {
        this.systemID = systemID;
    }

    @Override
    public String toString() {
        return "CommandEntity{" +
                "command='" + command + '\'' +
                ", commandID=" + commandID +
                ", systemID='" + systemID + '\'' +
                ", targetObject=" + targetObject +
                ", invocationTimestamp=" + invocationTimestamp +
                ", invokedBy=" + invokedBy +
                ", commandAttributes=" + commandAttributes +
                '}';
    }
}
