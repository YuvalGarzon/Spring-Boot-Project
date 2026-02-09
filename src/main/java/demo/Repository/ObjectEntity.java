package demo.Repository;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "OBJECTS")
public class ObjectEntity {
    @Id
    private String objectId;
    private String systemID;
    private String type;
    private String alias;
    private ObjectStatus status;
    private Boolean active;
    private Date creationTimestamp;
    private String userEmail;
    private String userSystemId;
    Map<String, Object> details;

    @DBRef
    private ObjectEntity parent;

    public ObjectEntity() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Boolean getActive() {
        return active;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public ObjectStatus getStatus() {
        return status;
    }

    public void setStatus(ObjectStatus status) {
        this.status = status;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSystemID() {
        return systemID;
    }

    public void setSystemID(String systemID) {
        this.systemID = systemID;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    public ObjectEntity getParent() {
        return parent;
    }

    public void setParent(ObjectEntity parent) {
        this.parent = parent;
    }

    public String getUserSystemId() {
        return userSystemId;
    }

    public void setUserSystemId(String userSystemId) {
        this.userSystemId = userSystemId;
    }

    @Override
    public String toString() {
        return "ObjectEntity [objectId=" + objectId + ", systemID=" + systemID + ", type=" + type + ", alias=" + alias
                + ", status=" + status + ", active=" + active + ", creationTimestamp=" + creationTimestamp
                + ", userEmail=" + userEmail + ", userSystemId=" + userSystemId + ", details=" + details + "]";
    }


}
