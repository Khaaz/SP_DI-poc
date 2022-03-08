package NoDefaultConstructor;

import lib.annotations.Inject;
import lib.annotations.Injectable;

@Injectable
public class InjectableTest {
    @Inject
    private InjectableChild child;

    @Inject
    private NoDefaultConstructor noDefConstruct;

    public InjectableChild getChild() {
        return child;
    }
}
