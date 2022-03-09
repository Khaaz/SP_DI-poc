package lib;

import lib.annotations.Inject;
import lib.annotations.Injectable;
import lib.exceptions.InstantiationFailedException;
import lib.exceptions.NoDefaultConstructorException;
import lib.exceptions.NonInjectableException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationParser {

    private final InjectorContainer container;

    public AnnotationParser(InjectorContainer container) {
        this.container = container;
    }

    boolean isInjectable(Class<?> c) {
        return c.isAnnotationPresent(Injectable.class);
    }

    /**
     * Inject all classes in their respective fields if they have the @Inject annotation.
     * Register the class in the container if it doesn't exist.
     * @param o
     * @throws NoDefaultConstructorException
     * @throws IllegalAccessException
     * @throws NonInjectableException
     * @throws InstantiationFailedException
     */
    void injectField(Object o) throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException, InstantiationFailedException {
        Class<?> c = o.getClass();
        for (Field f : c.getDeclaredFields()) {
            if (f.isAnnotationPresent(Inject.class)) {
                f.setAccessible(true);
                f.set(o, container.getOrRegister(f.getType()));
            }
        }
    }

    /**
     * Inject all classes in the constructor if it has an @Inject annotation.
     * @param c
     * @return
     * @throws NoDefaultConstructorException
     * @throws NonInjectableException
     * @throws IllegalAccessException
     * @throws InstantiationFailedException
     */
    Object injectConstructor(Class<?> c) throws NoDefaultConstructorException, NonInjectableException, IllegalAccessException, InstantiationFailedException {
        List<Object> list = new ArrayList<>();
        for (Constructor<?> construct : c.getDeclaredConstructors()) {
            if (construct.isAnnotationPresent(Inject.class)) {
                for (Class<?> parameterType : construct.getParameterTypes()) {
                    list.add(container.getOrRegister(parameterType));
                }

                try {
                    return construct.newInstance(list.toArray());
                } catch(Exception err) {
                    throw new InstantiationFailedException();
                }
            }
        }
        return null;
    }
}
