package pl.dziedziul.diyioccontainer.beannotfound;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import pl.dziedziul.myowndicontainer.engine.ContextFactory;

public class BeanNotFoundTest {
    @Test
    public void shouldNotFindBean() {
        //given
        var contextFactory = new ContextFactory(BeanNotFoundTest.class.getPackageName());
        //when
        Assertions.assertThatThrownBy(contextFactory::createContext)
                .hasMessage("Bean ImNotABean not found");
    }
}
