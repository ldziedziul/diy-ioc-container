package pl.dziedziul.myowndicontainer.engine;

class BeanCurrentlyInCreationException extends RuntimeException {
    BeanCurrentlyInCreationException(Class<?> clazz) {
        super("Bean " + clazz.getSimpleName() + " is already in creation");
    }
}
