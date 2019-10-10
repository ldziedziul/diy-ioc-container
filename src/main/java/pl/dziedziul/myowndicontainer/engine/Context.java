package pl.dziedziul.myowndicontainer.engine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Context {
    private final Map<Class<?>, Object> beans = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getBean(final Class<T> clazz) {
        Object beanInstance = Optional.ofNullable(beans.get(clazz)).orElseThrow(() -> new BeanNotFoundException(clazz));
        return (T) beanInstance;
    }
}

