package org.springframework.aop;


/**
 * 切点抽象: 进行匹配 只有匹配成功才会执行通知的增强
 *
 * @author derekyi
 * @date 2020/12/5
 */
public interface Pointcut {

	/**
	 * 根据类型进行过滤
	 * @return
	 */
	ClassFilter getClassFilter();

	/**
	 * 根据method进行匹配
	 * @return
	 */
	MethodMatcher getMethodMatcher();
}
