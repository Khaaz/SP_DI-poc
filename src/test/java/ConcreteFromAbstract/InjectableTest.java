package ConcreteFromAbstract;

import lib.annotations.Inject;
import lib.annotations.Injectable;

@Injectable
public class InjectableTest {
    @Inject
    private InjectableParent child;

    public InjectableParent getChild() {
        return child;
    }
}
