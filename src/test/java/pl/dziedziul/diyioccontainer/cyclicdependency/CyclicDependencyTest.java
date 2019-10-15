package pl.dziedziul.diyioccontainer.cyclicdependency;

import org.junit.Test;
import pl.dziedziul.myowndicontainer.engine.ContextFactory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CyclicDependencyTest {

    @Test
    public void shouldFailWithCyclicDependency() {
        //given
        var contextFactory = new ContextFactory(CyclicDependencyTest.class.getPackageName());
        //then
        assertThatThrownBy(contextFactory::createContext)
                .hasMessageContaining("is already in creation");
    }
}
