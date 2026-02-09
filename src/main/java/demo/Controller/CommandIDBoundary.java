package demo.Controller;

public class CommandIDBoundary {

    private String commandID;
    private String systemID;

    public CommandIDBoundary() {
    }

    public CommandIDBoundary(String commandID, String systemID) {
        this.commandID = commandID;
        this.systemID = systemID;
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
        return "CommandIDBoundary [commandID=" + commandID + ", systemID=" + systemID + "]";
    }
}
