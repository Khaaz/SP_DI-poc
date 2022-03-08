package NonExistingDep;

import lib.annotations.Inject;
import lib.annotations.Injectable;

@Injectable
public class InjectableTest {
    @Inject
    private InjectableChild child;

    @Inject
    private NonInjectable nonInjectable;

    public InjectableChild getChild() {
        return child;
    }

    public NonInjectable getNonInjectable() {
        return nonInjectable;
    }
}
