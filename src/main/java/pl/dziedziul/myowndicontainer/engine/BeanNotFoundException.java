package pl.dziedziul.myowndicontainer.engine;

class BeanNotFoundException extends RuntimeException {
    BeanNotFoundException(Class<?> clazz) {
        super("Bean " + clazz.getSimpleName() + " not found");
    }
}
