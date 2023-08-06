package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    /**
     * 인터페이스가 아닌 구현 클래스 ConcreteService에
     * 동적 프록시를 생성하기 위해 CGLIB를 활용한다.
     */
    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        // CGLIB를 활용하여 프록시 객체를 생성하는 방법.
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));
        ConcreteService proxy = (ConcreteService) enhancer.create();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();

    }
}
