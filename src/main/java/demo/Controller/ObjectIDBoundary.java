package demo.Controller;

public class ObjectIDBoundary {

    private String objectId;
    private String systemID;

    public ObjectIDBoundary() {
    }

    public ObjectIDBoundary(String objectId, String systemId) {
        this.objectId = objectId;
        this.systemID = systemId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSystemID() {
        return systemID;
    }

    public void setSystemID(String systemID) {
        this.systemID = systemID;
    }

    @Override
    public String toString() {
        return "ObjectIDBoundary [objectId=" + objectId + ", systemID=" + systemID + "]";
    }


}
