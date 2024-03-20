package org.springframework.test.aop;

import org.junit.Test;
import org.springframework.aop.mock.Proxy;
import org.springframework.aop.mock.Target;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib动态代理的运行案例
 * invoke和invokeSuper内部使用FastClass来避免反射
 */
public class ProxyTest extends Target {
    @Test
    public void testCglibProxy() throws Exception {
        Proxy proxy = new Proxy();
        Target target = new Target();
        proxy.setMethodInterceptor(new MethodInterceptor() {
            @Override
            public Object intercept(Object proxyObj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("before...");
                // return method.invoke(target, args); // 内部使用反射调用
                // return methodProxy.invoke(target, args); // 内部不使用反射, 需要结合目标使用
                return methodProxy.invokeSuper(proxyObj, args); // 内部不使用反射, 需要结合代理对象使用
            }
        });

        proxy.save();
    }
}
