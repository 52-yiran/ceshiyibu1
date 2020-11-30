package com.ceshi.annotation.impl;


import com.ceshi.annotation.ControllerEndpoint;
import com.ceshi.pojo.Other;
import com.ceshi.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 注解的实现类 要做的逻辑
 */

//@EnableAspectJAutoProxy
@Slf4j
@Aspect
@Component
public class ControllerEndpointAspectMy {


  // 这个写法不需要 切面的切入点了
  /*@Pointcut("@annotation(ControllerEndpoint)")
  public void pointcut() {

  }*/

  @Around("@annotation(controllerEndpoint)")
  public Object around (ProceedingJoinPoint point,ControllerEndpoint controllerEndpoint){
    Object result =null;
    Other other = new Other(5,"HELLO","标题");
    Object[] args = point.getArgs();
    for (int i = 0; i < args.length; i++) {
      Object object = args[i]; //// 每一个参数对象
      Field[] declaredFields = object.getClass().getDeclaredFields(); //获取所有的成员变量不考虑修饰符
      //declaredFields            private java.lang.Integer com.ceshi.pojo.Other.id
      //declaredFields            private java.lang.String com.ceshi.pojo.Other.username
      //declaredFields            private java.lang.String com.ceshi.pojo.Other.title
      for (int j = 0; j < declaredFields.length; j++) {
        Field declaredField = declaredFields[i];

        if(declaredField.getType() == Other.class){
          declaredField.setAccessible(true); // 暴力反射
          try {
            declaredField.set(object,other); // 把other 属性都设置到 object中
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        }
      }
    }


    //Method method = resolveMethod(point);
    //ControllerEndpoint annotation = method.getAnnotation(ControllerEndpoint.class);

    MethodSignature signature = (MethodSignature)point.getSignature();
    Method method1 = signature.getMethod(); // 可以利用反射来调用对象就执行了方法
    //String name = method.getName();
    String name = method1.getName();
    System.out.println("方法名字:"+name); //方法名字:getAll

    String name1 = signature.getName();
    System.out.println("name1:"+name1);//name1:getAll   方法的名字

    Class<?> targetClass = point.getTarget().getClass();
    String name2 = targetClass.getName(); // 类的名字
    System.out.println("类的名字:"+name2); //类的名字:com.ceshi.controller.MyController

    String s = controllerEndpoint.exceptionMessage();
    String operation = controllerEndpoint.operation();
    log.info("s:{},operation:{}",s,operation); // s:确实是的,operation:你是大锤
    try {
      // 获取得到传递的参数
      result = point.proceed();
      // 获取ip
      String ipAddr = IpUtil.getIpAddr();
      //
      //operationLogService.saveLog(point, targetMethod, ip, operation, start);
      log.info("ipAddr:{}",ipAddr); // ipAddr:127.0.0.1
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
    return result;
  }

  private Method resolveMethod(ProceedingJoinPoint point) {
    // Signature signature = point.getSignature();
    //获取方法签名的
    MethodSignature signature =(MethodSignature) point.getSignature();

    // 这个method1 和下面的获取method功能 一样
    Method method1 = signature.getMethod(); // 可以利用反射来调用对象就执行了方法

    Class<?> targetClass = point.getTarget().getClass();

    String name = targetClass.getName(); // 类的名字
    System.out.println("类的名字:"+name); //类的名字:com.ceshi.controller.MyController

    String name1 = signature.getName();
    System.out.println("name1:"+name1);//name1:getAll   方法的名字
    Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
    for (Class<?> parameterType : parameterTypes) {
      System.out.println(parameterType.getName());//  这个name 是Other 的类型   com.ceshi.pojo.Other
      /*
          {
            "username":"laowang",
              "title":"哈哈哈"
          }

      */
    }
    Method method = getDeclaredMethod(targetClass, signature.getName(), parameterTypes);
    if (method == null) {
      throw new IllegalStateException("无法解析目标方法: " + signature.getMethod().getName());
    }
    //return method;
    return method1;

  }


  private Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
    try {
      return clazz.getDeclaredMethod(name, parameterTypes);
    } catch (NoSuchMethodException e) {
      Class<?> superClass = clazz.getSuperclass();
      if (superClass != null) {
        return getDeclaredMethod(superClass, name, parameterTypes);
      }
    }
    return null;
  }
}
