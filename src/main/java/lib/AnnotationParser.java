package lib;

import lib.annotations.Inject;
import lib.annotations.Injectable;
import lib.exceptions.NoDefaultConstructorException;
import lib.exceptions.NonInjectableException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class AnnotationParser {

    private final InjectorContainer container;

    public AnnotationParser(InjectorContainer container) {
        this.container = container;
    }

    void injectField(Object o) throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException {
        Class<?> c = o.getClass();
        for (Field f : c.getDeclaredFields()) {
            if (f.isAnnotationPresent(Inject.class)) {
                f.setAccessible(true);
                f.set(o, container.getOrRegister(f.getType()));
            }
        }
    }

    boolean isInjectable(Class<?> c) {
        return c.isAnnotationPresent(Injectable.class);
    }

    void injectConstructor(Class<?> c) {
        for (Constructor<?> construct : c.getDeclaredConstructors()) {
            if (construct.isAnnotationPresent(Inject.class)) {
                System.out.println(construct.getName());
            }
        }
    }



}
