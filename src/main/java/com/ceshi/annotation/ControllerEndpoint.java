package com.ceshi.annotation;


import java.lang.annotation.*;

@Documented // jdk生成api 的文档使用
@Target(ElementType.METHOD) //注解可以加到那个地方 ElementType.METHOD  加到方法上     ElementType.TYPE 加到类上面加到属性上面
@Retention(RetentionPolicy.RUNTIME) // 保留这个注解在运行的时候
public @interface ControllerEndpoint {

  String operation() default "";

  String exceptionMessage() default "系统内部异常";
}
