import lib.InjectorContainer;
import lib.exceptions.NoDefaultConstructorException;
import lib.exceptions.NonInjectableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContainerTest {
    InjectorContainer injector;

    @BeforeEach
    void createInjector() {
        injector = new InjectorContainer();
    }


    @Test
    @DisplayName("Test parse static")
    void parseStatic() throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException {
        injector.parseStatic("src");

        // cast manually to Test (for the example)
        src.Test t1 = injector.get(src.Test.class);

        t1.getChild().incrementCounter();

        src.Test t2 = injector.get(src.Test.class);
        t2.getChild().incrementCounter();
        t2.getChild().incrementCounter();
        t2.getChild().incrementCounter();
        assertEquals(t1.getChild().getCounter(), t2.getChild().getCounter());
    }

    @Test
    @DisplayName("Test injection of a concrete class from abstract declaration")
    void getInstanceFromAbstract() throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException {
        injector.register(ConcreteFromAbstract.InjectableParent.class, ConcreteFromAbstract.InjectableChild.class);
        injector.parseStatic("ConcreteFromAbstract");

        ConcreteFromAbstract.InjectableTest t1 = injector.get(ConcreteFromAbstract.InjectableTest.class);

        assertTrue(t1.getChild() instanceof ConcreteFromAbstract.InjectableChild);
    }

    @Test
    @DisplayName("Inject with a non injectable class")
    void nonInjectableError() {
        assertThrows(NonInjectableException.class, () -> injector.parseStatic("NonExistingDep"));
    }

    @Test
    @DisplayName("Inject a class that doesn't have a default constructor")
    void noDefaultConstructorError() {
        assertThrows(NoDefaultConstructorException.class, () -> injector.parseStatic("NoDefaultConstructor"));
    }
}
