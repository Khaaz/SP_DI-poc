package src;

import lib.annotations.Inject;
import lib.annotations.Injectable;

@Injectable
public class Test {
    @Inject
    private InjectableChild child;

    private NonInjectable nonInjectable;

    public InjectableChild getChild() {
        return child;
    }

    public NonInjectable getNonInjectable() {
        return nonInjectable;
    }
}
