package pl.dziedziul.myowndicontainer.engine;

public interface BeanPostProcessor {
    default Object postProcess(Object bean) {
        return bean;
    }
}
