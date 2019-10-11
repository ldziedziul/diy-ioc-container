package pl.dziedziul.myowndicontainer.engine;

import java.lang.reflect.Method;
import java.util.List;

class BeanDefinition {
    private final Class<?> beanType;
    private final List<Class<?>> dependencies;
    private final Object configuration;
    private final Method beanMethod;

    BeanDefinition(final Class<?> beanType, final List<Class<?>> dependencies, final Object configuration, final Method beanMethod) {
        this.beanType = beanType;
        this.dependencies = dependencies;
        this.configuration = configuration;
        this.beanMethod = beanMethod;
    }

    Class<?> getBeanType() {
        return beanType;
    }

    List<Class<?>> getDependencies() {
        return dependencies;
    }

    Object createInstance(final Object[] constructorArgs) {
        try {
            beanMethod.setAccessible(true);
            return beanMethod.invoke(configuration, constructorArgs);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Error creating bean " + beanType.getSimpleName(), e);
        }
    }

}
