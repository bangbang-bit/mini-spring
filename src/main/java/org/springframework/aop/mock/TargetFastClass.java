package org.springframework.aop.mock;

import org.springframework.cglib.core.Signature;

import java.util.Objects;

public class TargetFastClass {
    static Signature s0 = new Signature("save", "()V");

    /**
     * 根据方法的签名信息获取对应的方法标识
     * 每个methodProxy创建时执行 都会生成对应的方法标识
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
     * @param target
     * @param args
     * @return
     */
    public Object invoke(int index, Object target, Object[] args) {
        if (index == 0){
            // 正常调用 不经过反射
            ((Target) target).save();
            return null;
        } else {
            throw new RuntimeException("方法未定义");
        }
    }

}
