import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Server extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<>();
        set.add(MandelbrotRestService.class);
        return set;
    }

    @Override
    public Set<Object> getSingletons() {
        return Collections.emptySet();
        //return super.getSingletons();
    }
}
