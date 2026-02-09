package demo.Helper;

import demo.Controller.ObjectBoundary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObjectHelper {

    public ObjectHelper() {
    }

    public void filterNonActive(List<ObjectBoundary> objects) {
        objects.removeIf(obj -> !Boolean.TRUE.equals(obj.isActive()));
    }


}
