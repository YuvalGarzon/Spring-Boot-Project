package demo.Controller;

import demo.Repository.ObjectStatus;

import java.util.Date;
import java.util.Map;

public class ObjectBoundary {
    private ObjectIDBoundary id;
    private String type;
    private String alias;
    private ObjectStatus status;
    private Boolean active;
    private Date creationTimestamp;
    private UserIDBoundary createdBy;
    private Map<String, Object> details;

    public ObjectBoundary() {
    }

    public ObjectIDBoundary getId() {
    	
        return id;
    }

    public void setId(ObjectIDBoundary id) {
        this.id = id;
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

    public UserIDBoundary getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserIDBoundary createdBy) {
        this.createdBy = createdBy;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Object{" + "id=" + id + ", type='" + type + '\'' + ", alias='" + alias + '\'' + ", status='" + status
                + '\'' + ", active=" + active + ", creationTimestamp='" + creationTimestamp + '\'' + ", createdBy="
                + createdBy + ", objectDetails=" + details + '}';
    }

}
