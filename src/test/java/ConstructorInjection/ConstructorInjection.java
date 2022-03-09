package ConstructorInjection;

import lib.annotations.Inject;
import lib.annotations.Injectable;

@Injectable
public class ConstructorInjection {
    int counter = 0;

    public void incrementCounter() {
        counter++;
    }
    public void decrementCounter() {
        counter--;
    }

    public int getCounter() {
        return counter;
    }

    public InjectableChild child;

    @Inject
    public ConstructorInjection(InjectableChild child) {
        counter = child.getCounter();
        this.child = child;
    }
}

