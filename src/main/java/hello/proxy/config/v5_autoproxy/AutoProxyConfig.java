package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.config.v4_postprocessor.BeanPostProcessorConfig;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
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
     * <p>
     * 다만, NameMatchMethodPointcut을 사용하게 된다면, 메소드에 request가 포함된 다른 스프링 빈에도 프록시가 적용되는 문제가 있다.
     */
//    @Bean
    public Advisor advisor1(LogTrace logTrace) {
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }


    /**
     * AspectJExpressionPointcut
     * <p>
     * AOP에 특화된 포인트컷 표현식을 적용할 수 있다
     */
//    @Bean
    public Advisor advisor2(LogTrace logTrace) {
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..))");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    /**
     * noLog()를 제외한 메소드에 대해서만 advice를 적용하도록 표현식을 수정.
     */
    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");
        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
