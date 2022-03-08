package ConcreteFromAbstract;

import lib.annotations.Injectable;

@Injectable
public class InjectableParent {
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
}
