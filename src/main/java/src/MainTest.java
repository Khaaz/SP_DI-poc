package src;

import lib.InjectorContainer;

// Equivalent to Spring-Boot start class
public class MainTest {
    private static final InjectorContainer injector = new InjectorContainer();

    public static void run(Class<?> mainClass) throws Exception {
        injector.register(InjectableParent.class, InjectableChild.class);
        injector.parseStatic("src");

        // cast manually to Test (for the example)
        Test t = (Test)injector.get(mainClass);

        t.getChild().incrementCounter();
        System.out.println(t.getChild().getCounter());
    }
}
