# SP_DI-poc

## API

The `InjectorContainer` class is responsible for handling the injection process, and holding all instances.  

### parseStatic

Parse all files in a package and register them in the DI container.

```java
public void parseStatic(String packageName)
```

### register

Register a class in the DI container.

```java
public void register(Class<?> c)
```

Register a class in the DI container mapped to an abstract class.

```java
public void register(Class<?> abstractC, Class<?> concreteC)
```

### get

Get an instance for a class in the DI container.

```java
public <T> T get(Class<T> c)
```

### @Inject

On a field or a constructor, mark this field/constructor to benefit from DI.

### @Injectable

On a class, mark this class to benefit from DI, and be Injected in other classes.

## Features

- Constructor injection with `@Inject`
- Field injection with `@Inject`
- Package scanning via `parseStatic`
- `@Injectable` to specify injectable Class, and class that will benefit from DI. 
- Cyclic DI, so all classes get Injected until all classes are instantiated
- Error handling to automatically stop the DI process in case of missing constructor, instantiation error, non `@Injectable` injection

## Tests

The `src` package acts as a default template app, easily customisable to test the DI container.

The `test` package tests various scenario:
- Static file parsing
- Constructor injection
- Field injection
- Class without default constructor
- Class with a non-injectable class