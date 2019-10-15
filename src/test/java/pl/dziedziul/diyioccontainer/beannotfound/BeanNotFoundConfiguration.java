package pl.dziedziul.diyioccontainer.beannotfound;

import pl.dziedziul.myowndicontainer.engine.Bean;
import pl.dziedziul.myowndicontainer.engine.Configuration;

@Configuration
class BeanNotFoundConfiguration {

    @Bean
    SomeService userService(ImNotABean imNotABean) {
        return new SomeService(imNotABean);
    }
}
