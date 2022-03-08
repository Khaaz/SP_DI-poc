package NonExistingDep;

public class NonInjectable {
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

