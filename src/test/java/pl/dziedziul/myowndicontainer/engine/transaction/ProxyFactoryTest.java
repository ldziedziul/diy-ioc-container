package pl.dziedziul.myowndicontainer.engine.transaction;

import org.junit.Test;
import pl.dziedziul.myowndicontainer.engine.ProxyFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProxyFactoryTest {

    @Test
    public void createProxy() throws Exception {
        //given
        var sut = new ProxyFactory();
        //when
        final SomeService target = new SomeService(123);
        SomeService proxy = sut.createProxy(target.getClass(), new TransactionalHandler(target));
        //then
        proxy.getNumber();
        proxy.doSomething();
        assertThatThrownBy(proxy::doSomething).isInstanceOf(RuntimeException.class)
                .hasMessage("Validation failed or sth similar");
    }
}