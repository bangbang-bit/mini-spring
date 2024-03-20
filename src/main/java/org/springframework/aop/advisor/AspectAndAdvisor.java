package org.springframework.aop.advisor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.InvocationTargetException;

public class AspectAndAdvisor {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("aspect", AdvanceAspect.class);
        context.registerBean("config", Config.class);
        context.registerBean(ConfigurationClassPostProcessor.class);
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class);

        context.refresh();
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }

        /*Map<String, AnnotationAwareAspectJAutoProxyCreator> beans = context.getBeansOfType(AnnotationAwareAspectJAutoProxyCreator.class);
        for (AnnotationAwareAspectJAutoProxyCreator creator : beans.values()) {
            Method method = creator.getClass().getMethod("findCandidateAdvisors");
            List<Advisor> advisors = (List<Advisor>) method.invoke(creator);
            for (Advisor advisor : advisors) {
                System.out.println("advisor = " + advisor);
            }
        }*/

    }
    static class Target{
        public void foo(){
            System.out.println("foo...");
        }
    }

    @Aspect //高级切面类
    static class AdvanceAspect {
        // 为切面类定义通知方法
        @Before("execution(* foo())")
        public void beforeAspect(){
            System.out.println("Aspect#before");
        }

        @After("execution(* bar())")
        public void AfterAspect(){
            System.out.println("Aspect#after");
        }
    }

    @Configuration
    static class Config{
        @Bean
        public Advisor advisor(MethodInterceptor interceptor){
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* foo())");
            return new DefaultPointcutAdvisor(pointcut, interceptor);
        }
        @Bean
        public MethodInterceptor interceptor(){
            return invocation -> {
                System.out.println("MethodInterceptor#before");
                Object proceed = invocation.proceed();
                System.out.println("MethodInterceptor#after");
                return proceed;
            };
        }
    }


}
