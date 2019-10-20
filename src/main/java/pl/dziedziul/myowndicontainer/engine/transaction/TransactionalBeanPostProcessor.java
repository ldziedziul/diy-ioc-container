package pl.dziedziul.myowndicontainer.engine.transaction;

import pl.dziedziul.myowndicontainer.engine.BeanPostProcessor;
import pl.dziedziul.myowndicontainer.engine.ProxyFactory;

public class TransactionalBeanPostProcessor implements BeanPostProcessor {
    private final ProxyFactory proxyFactory = new ProxyFactory();

    @Override
    public Object postProcess(final Object bean) {
        Class<?> beanType = bean.getClass();
        if (beanType.isAnnotationPresent(Transactional.class)) {
            return proxyFactory.createProxy(beanType, new TransactionalHandler(bean));
        } else {
            return bean;
        }
    }
}
