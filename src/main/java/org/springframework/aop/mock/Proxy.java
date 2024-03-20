package org.springframework.aop.mock;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.UndeclaredThrowableException;

import java.lang.reflect.Method;

public class Proxy extends Target{
    private MethodInterceptor methodInterceptor;
    static Method save0;
    static MethodProxy save0Proxy;

    static {
        try {
            save0 = Target.class.getMethod("save");
            // 目标类型 代理类型 无参无返回值 带增强功能的方法名 带原始功能的方法名
            save0Proxy = MethodProxy.create(Target.class, Proxy.class, "()V", "save", "originalSave");
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    /**
     * 重写后具有增强功能的save方法
     */
    @Override
    public void save() {
        try {
            methodInterceptor.intercept(this, save0, new Object[0], save0Proxy);
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    /**
     * 原始save方法 为了创建methodProxy对象
     */
    public void originalSave() {
        super.save();
    }
}
