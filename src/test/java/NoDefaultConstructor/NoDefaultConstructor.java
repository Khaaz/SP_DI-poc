package NoDefaultConstructor;

import lib.annotations.Injectable;

@Injectable
public class NoDefaultConstructor {
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

    NoDefaultConstructor(int counter) {
        this.counter = counter;
    }
}

