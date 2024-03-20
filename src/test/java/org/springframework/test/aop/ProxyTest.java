package org.springframework.test.aop;

import org.junit.Test;
import org.springframework.aop.mock.Proxy;
import org.springframework.aop.mock.Target;

/**
 * cglib动态代理的运行案例
 * invoke和invokeSuper内部使用FastClass来避免反射
 *
 * jdk: 一个方法调用对应一个代理类
 * cglib: 一个代理类会对应两个FastClass 一个FastClass是配合目标对象使用的  一个FastClass是配合代理使用的
 */
public class ProxyTest extends Target {
    @Test
    public void testCglibProxy() throws Exception {
        Proxy proxy = new Proxy();
        Target target = new Target();
        proxy.setMethodInterceptor((proxyObj, method, args, methodProxy) -> {
            System.out.println("before...");
            // return method.invoke(target, args); // 内部使用反射调用
            // return methodProxy.invoke(target, args); // 内部不使用反射, 需要结合目标使用
            return methodProxy.invokeSuper(proxyObj, args); // 内部不使用反射, 需要结合代理对象使用
        });

        proxy.save();
    }
}
