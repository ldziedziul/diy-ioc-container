package pl.dziedziul.diyioccontainer.simple;

import org.junit.Test;
import pl.dziedziul.myowndicontainer.engine.Context;
import pl.dziedziul.myowndicontainer.engine.ContextFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SimpleTest {
    @Test
    public void shouldStartContext() {
        //given
        var contextFactory = new ContextFactory(SimpleTest.class.getPackageName());
        Context context = contextFactory.createContext();
        //when
        UserService userService = context.getBean(UserService.class);
        //then
        userService.printUsers();
    }

    @Test
    public void shouldNotFindBean() {
        //given
        var contextFactory = new ContextFactory(SimpleTest.class.getPackageName());
        Context context = contextFactory.createContext();
        //when
        assertThatThrownBy(() -> context.getBean(ImNotABean.class))
                .hasMessage("Bean ImNotABean not found");
    }
}
