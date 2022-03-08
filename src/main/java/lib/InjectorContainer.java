package lib;

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

    private <T> T _register(Class<?> abstractC, Class<T> concreteC) throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException {
        Object e;
        try {
            e = concreteC.getDeclaredConstructor().newInstance();
        } catch(Exception err) {
            throw new NoDefaultConstructorException();
        }
        classes.put(abstractC, e);
        annotationParser.injectField(e);
        return (T)e;
    }

    public void parseStatic(String packageName) throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException {
        Set<Class<?>> classes = StaticParser.findAllClasses(packageName);
        for (Class<?> c : classes) {
            if (annotationParser.isInjectable(c)) {
                this.register(c);
            }
        }
    }

    public void register(Class<?> c) throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException {
        if (classes.containsKey(c)) {
            return;
        }
        this._register(c, c);
    }

    public void register(Class<?> abstractC, Class<?> concreteC) throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException {
        if (classes.containsKey(abstractC)) {
            return;
        }

        this._register(abstractC, concreteC);
    }

     <T> T getOrRegister(Class<T> c) throws NoDefaultConstructorException, IllegalAccessException, NonInjectableException {
        if (classes.containsKey(c)) {
            return (T)classes.get(c);
        }

        if (!annotationParser.isInjectable(c)) {
            throw new NonInjectableException();
        }
        return this._register(c, c);
    }

    public <T> T get(Class<T> c) {
        return (T)classes.get(c);
    }
}
