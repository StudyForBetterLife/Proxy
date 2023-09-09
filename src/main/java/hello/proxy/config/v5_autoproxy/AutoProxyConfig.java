package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.config.v4_postprocessor.BeanPostProcessorConfig;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    /**
     * spring-boot-starter-aop 의존성을 추가하면, AnnotationAwareAspectJAutoProxyCreator가 빈으로 등록된다.
     * <p>
     * <h1>AnnotationAwareAspectJAutoProxyCreator</h1>
     * 이 클래스는 bean으로 등록된 Advisor를 모두 찾아 자동으로 프록시를 생성해준다.
     * {@link BeanPostProcessorConfig}처럼 빈 후처리기를 통해 프록시를 빈으로 등록해줄 필요가 없어진다.
     */
    @Bean
    public Advisor advisor1(LogTrace logTrace) {
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
