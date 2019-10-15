package pl.dziedziul.myowndicontainer.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

class BeanFactory {
    private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);

    private final Set<BeanDefinition> beanDefinitions;

    public BeanFactory(final Set<BeanDefinition> beanDefinitions) {
        this.beanDefinitions = beanDefinitions;
    }

    public void init(Context context) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Class<?> beanType = beanDefinition.getBeanType();
            if (!context.containsBean(beanType)) {
                Object bean = createBean(beanDefinition, context);
                context.registerBean(beanType, bean);
            }
        }
    }

    Object createBean(BeanDefinition beanDefinition, Context context) {
        Class<?> beanType = beanDefinition.getBeanType();
        log.info("Creating bean {}", beanType.getName());
        Object[] constructorArgs = beanDefinition.getDependencies().stream()
                .map(depType -> getOrCreateDependencyBean(context, depType)).toArray();
        return beanDefinition.createInstance(constructorArgs);
    }

    private Object getOrCreateDependencyBean(final Context context, final Class<?> beanType) {
        if (context.containsBean(beanType)) {
            return context.getBean(beanType);
        } else {
            BeanDefinition dependencyBeanDefinition = getBeanDefinition(beanType);
            Object bean = createBean(dependencyBeanDefinition, context);
            context.registerBean(beanType, bean);
            return bean;
        }

    }

    private BeanDefinition getBeanDefinition(final Class<?> beanType) {
        return beanDefinitions.stream()
                .filter(def -> def.getBeanType().equals(beanType))
                .findFirst()
                .orElseThrow(() -> new BeanNotFoundException(beanType));
    }

}
