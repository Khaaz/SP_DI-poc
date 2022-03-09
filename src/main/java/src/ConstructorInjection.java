package src;

import lib.annotations.Inject;
import lib.annotations.Injectable;

@Injectable
public class ConstructorInjection {
    @Inject
    public ConstructorInjection(InjectableChild child) {
        System.out.println("CONSTRUCTOR INJECTION");
        child.incrementCounter();
        child.incrementCounter();
        child.incrementCounter();
        System.out.println(child.getCounter());
    }
}
