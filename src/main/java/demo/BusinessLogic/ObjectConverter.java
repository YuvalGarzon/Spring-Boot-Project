package demo.BusinessLogic;

import demo.Controller.ObjectBoundary;
import demo.Repository.ObjectEntity;
import demo.Controller.ObjectIDBoundary;
import org.springframework.stereotype.Component;

import demo.Controller.UserIDBoundary;

@Component
public class ObjectConverter {
    public ObjectEntity toEntity(ObjectBoundary boundary) {
        ObjectEntity rv = new ObjectEntity();

        if (boundary.getId().getObjectId() != null) {
            rv.setObjectId(boundary.getId().getObjectId());
        } else {
            rv.setObjectId(null);
        }

        if (boundary.getId().getSystemID() != null) {
            rv.setSystemID(boundary.getId().getSystemID());
        } else {
            rv.setSystemID(null);
        }

        rv.setType(boundary.getType());
        rv.setAlias(boundary.getAlias());
        rv.setStatus(boundary.getStatus());
        rv.setActive(boundary.isActive());
        rv.setCreationTimestamp(boundary.getCreationTimestamp());
        rv.setUserEmail(boundary.getCreatedBy().getEmail());
        rv.setUserSystemId(boundary.getCreatedBy().getSystemID());
        rv.setDetails(boundary.getDetails());

        return rv;

    }

    public ObjectBoundary fromEntity(ObjectEntity entity) {
        ObjectBoundary rv = new ObjectBoundary();

        rv.setId(new ObjectIDBoundary(entity.getObjectId(), entity.getSystemID()));
        rv.setType(entity.getType());
        rv.setAlias(entity.getAlias());
        rv.setActive(entity.isActive());
        rv.setStatus(entity.getStatus());
        rv.setCreationTimestamp(entity.getCreationTimestamp());
        rv.setCreatedBy(new UserIDBoundary(entity.getUserEmail(), entity.getSystemID()));
        rv.setDetails(entity.getDetails());

        return rv;
    }
}
