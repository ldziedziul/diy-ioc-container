package pl.dziedziul.myowndicontainer.engine;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;

public class ProxyFactory {
    private static Logger log = LoggerFactory.getLogger(ProxyFactory.class);

    public <T> T createProxy(final Class<T> targetClass, final InvocationHandler invocationHandler) {
        log.info("Creating transactional proxy for bean " + targetClass);
        Class<? extends T> proxyClass = getProxyClass(targetClass, invocationHandler);
        ObjectInstantiator<? extends T> instantiator = new ObjenesisStd().getInstantiatorOf(proxyClass);
        return instantiator.newInstance();
    }

    private <T> Class<? extends T> getProxyClass(final Class<T> targetClass, InvocationHandler invocationHandler) {
        final ClassLoader classLoader = targetClass.getClassLoader();
        final String proxyClassName = targetClass.getName() + "$$Proxy" + invocationHandler.getClass().getSimpleName();
        try {
            Class<? extends T> proxy = (Class<T>) Class.forName(proxyClassName, false, classLoader);
            log.info("Skipping creation of existing dynamic proxy class " + proxyClassName);
            return proxy;
        } catch (ClassNotFoundException e) {
            return new ByteBuddy()
                    .subclass(targetClass)
                    .name(proxyClassName)
                    .method(ElementMatchers.any())
                    .intercept(InvocationHandlerAdapter.of(invocationHandler))
                    .make()
                    .load(classLoader, ClassLoadingStrategy.Default.INJECTION).getLoaded();
        }
    }

}
