package lib;

import lib.exceptions.InstantiationFailedException;
import lib.exceptions.NoDefaultConstructorException;
import lib.exceptions.NonInjectableException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InjectorContainer {
    private final AnnotationParser annotationParser;
    private final Map<Class<?>, Object> classes = new HashMap<>();

    public InjectorContainer() {
        annotationParser = new AnnotationParser(this);
    }

    /**
     * Register a class in the container.
     * Inject constructor if it needs to, otherwise inject all fields.
     * @param abstractC
     * @param concreteC
     * @param <T>
     * @return
     * @throws NoDefaultConstructorException
     * @throws IllegalAccessException
     * @throws NonInjectableException
     * @throws InstantiationFailedException
     */
    private <T> T _register(Class<?> abstractC, Class<T> concreteC) throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException, InstantiationFailedException {
        Object e;
        e = annotationParser.injectConstructor(concreteC);
        try {
            // try to inject constructor if any injectable
            if (e == null) {
                e = concreteC.getDeclaredConstructor().newInstance();
            }
        } catch(Exception err) {
            throw new NoDefaultConstructorException();
        }
        classes.put(abstractC, e);
        annotationParser.injectField(e);
        return (T)e;
    }

    /**
     * Parse all classes in a package and register @Injectable class in the container.
     * @param packageName
     * @throws NoDefaultConstructorException
     * @throws IllegalAccessException
     * @throws NonInjectableException
     * @throws InstantiationFailedException
     */
    public void parseStatic(String packageName) throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException, InstantiationFailedException {
        Set<Class<?>> classes = StaticParser.findAllClasses(packageName);
        for (Class<?> c : classes) {
            if (annotationParser.isInjectable(c)) {
                this.register(c);
            }
        }
    }

    /**
     * Register a class in the container.
     * @param c
     * @throws NoDefaultConstructorException
     * @throws IllegalAccessException
     * @throws NonInjectableException
     * @throws InstantiationFailedException
     */
    public void register(Class<?> c) throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException, InstantiationFailedException {
        if (classes.containsKey(c)) {
            return;
        }
        this._register(c, c);
    }

    /**
     * Register a class in the container mapped to its abstraction.
     * @param abstractC
     * @param concreteC
     * @throws NoDefaultConstructorException
     * @throws IllegalAccessException
     * @throws NonInjectableException
     * @throws InstantiationFailedException
     */
    public void register(Class<?> abstractC, Class<?> concreteC) throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException, InstantiationFailedException {
        if (classes.containsKey(abstractC)) {
            return;
        }

        this._register(abstractC, concreteC);
    }

    /**
     * Internal method to resolve an instance or register it if it doesn't exist.
     * @param c
     * @param <T>
     * @return
     * @throws NoDefaultConstructorException
     * @throws IllegalAccessException
     * @throws NonInjectableException
     * @throws InstantiationFailedException
     */
     <T> T getOrRegister(Class<T> c) throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException, InstantiationFailedException {
        if (classes.containsKey(c)) {
            return (T)classes.get(c);
        }

        if (!annotationParser.isInjectable(c)) {
            throw new NonInjectableException();
        }
        return this._register(c, c);
    }

    /**
     * Get an instance from the container.
     * @param c
     * @param <T>
     * @return
     */
    public <T> T get(Class<T> c) {
        return (T)classes.get(c);
    }
}
