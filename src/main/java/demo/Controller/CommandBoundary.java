package demo.Controller;

import demo.Repository.CommandEntity;

import java.util.*;

public class CommandBoundary {
    private CommandIDBoundary id;
    private String command;
    private ObjectIDBoundary targetObject;
    private Date invocationTimestamp;
    private UserIDBoundary invokedBy;
    private Map<String, Object> commandAttributes;

    public CommandBoundary(CommandEntity commandEntity) {

        this.id = new CommandIDBoundary(commandEntity.getCommandID(), commandEntity.getSystemID());
        this.command = commandEntity.getCommand();
        this.targetObject = commandEntity.getTargetObject();
        this.invocationTimestamp = commandEntity.getInvocationTimestamp();
        this.invokedBy = commandEntity.getInvokedBy();
        this.commandAttributes = commandEntity.getCommandAttributes();
    }


    public CommandBoundary() {

    }

    public CommandIDBoundary getCommandId() {
        return id;
    }

    public void setCommandId(CommandIDBoundary id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Map<String, Object> getCommandAttributes() {
        return commandAttributes;
    }

    public Date getInvocationTimestamp() {
        return invocationTimestamp;
    }

    public UserIDBoundary getInvokedBy() {
        return invokedBy;
    }

    public ObjectIDBoundary getTargetObject() {
        return targetObject;
    }

    public void setCommandAttributes(Map<String, Object> commandAttributes) {
        this.commandAttributes = commandAttributes;
    }

    public void setCommandInvocationTimestamp(Date invocationTimestamp) {
        this.invocationTimestamp = invocationTimestamp;
    }

    public void setInvokedBy(UserIDBoundary invokedBy) {
        this.invokedBy = invokedBy;
    }

    public void setTargetObject(ObjectIDBoundary targetObject) {
        this.targetObject = targetObject;
    }

    @Override
    public String toString() {
        return "CommandBoundary{" + "id=" + id + ", command='" + command + '\'' + ", targetObject=" + targetObject + ", invocationTimestamp=" + invocationTimestamp + ", invokedBy=" + invokedBy + ", commandAttributes=" + commandAttributes + '}';
    }
}
