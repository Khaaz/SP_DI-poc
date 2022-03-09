package ConstructorInjection;

import lib.annotations.Inject;
import lib.annotations.Injectable;

@Injectable
public class InjectableTest {
    @Inject
    private InjectableChild child;

    @Inject
    private ConstructorInjection consInjection;

    public InjectableChild getChild() {
        return child;
    }

    public ConstructorInjection getConsInjection() {
        return consInjection;
    }
}
