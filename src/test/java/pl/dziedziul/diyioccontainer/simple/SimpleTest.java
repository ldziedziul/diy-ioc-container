package pl.dziedziul.diyioccontainer.simple;

import org.junit.Test;
import pl.dziedziul.myowndicontainer.engine.Context;
import pl.dziedziul.myowndicontainer.engine.ContextFactory;

import java.util.List;

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
        userService.print(List.of());
    }

    @Test
    public void shouldStartContextAndRollback() {
        //given
        var contextFactory = new ContextFactory(SimpleTest.class.getPackageName());
        Context context = contextFactory.createContext();
        //when
        UserService userService = context.getBean(UserService.class);
        //then
        assertThatThrownBy(userService::printUsersThrowing).isInstanceOf(RuntimeException.class)
                .hasMessage("some exception");
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
