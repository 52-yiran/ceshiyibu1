package com.deshi.config;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

//@EnableAspectJAutoProxy
@Slf4j
@Aspect
@Component
public class LogRecordAspect {

  // 定义切点Pointcut
  @Pointcut("execution(* com.deshi.controller.*Controller.*(..))")
  public void excudeService() {

  }


  @Around("excudeService()")
  public Object before(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes sra = (ServletRequestAttributes) ra;
    HttpServletRequest request = sra.getRequest();

    String method = request.getMethod();
    String uri = request.getRequestURI();
    Map<String, String[]> parameterMap = request.getParameterMap();
    String paraString = JSON.toJSONString(parameterMap);
    log.info("请求开始, URI: {}, method: {}, params: {}", uri, method, paraString);

    // result的值就是被拦截方法的返回值
    Object result = proceedingJoinPoint.proceed();
    log.info("请求结束，controller的返回值是 " + JSON.toJSONString(result));
    return result;
  }
}
