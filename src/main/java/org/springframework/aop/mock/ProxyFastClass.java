package org.springframework.aop.mock;

import org.springframework.cglib.core.Signature;

import java.util.Objects;

public class ProxyFastClass {
    static Signature s0 = new Signature("originalSave", "()V");

    /**
     * 获取代理方法的编号
     * @param signature
     * @return
     */
    public int getIndex(Signature signature){
        if (Objects.equals(s0, signature)){
            return 0;
        }
        // ...
        return -1;
    }

    /**
     * 根据方法标识 正常调用目标对象中的方法
     * @param index
     * @param proxy
     * @param args
     * @return
     */
    public Object invoke(int index, Object proxy, Object[] args) {
        if (index == 0){
            // 正常调用 不经过反射
            ((Proxy) proxy).originalSave();
            return null;
        } else {
            throw new RuntimeException("方法未定义");
        }
    }
}
