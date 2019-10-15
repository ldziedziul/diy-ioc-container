package pl.dziedziul.diyioccontainer.cyclicdependency;

import org.junit.Test;
import pl.dziedziul.myowndicontainer.engine.ContextFactory;

public class CyclicDependencyTest {

    @Test
    public void shouldFailWithCyclicDependency() {
        //given
        var contextFactory = new ContextFactory(CyclicDependencyTest.class.getPackageName());
        //when
        contextFactory.createContext();
        //then
        //??
    }
}
