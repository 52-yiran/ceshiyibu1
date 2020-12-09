package com.ceshi.annotation.impl;

import com.ceshi.annotation.Limit;
import com.ceshi.annotation.LimitType;
import com.ceshi.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 * ip 限流的操作
 */

@Slf4j
@Component
@Aspect
public class LimitAspect {

  @Autowired
  private RedisTemplate<String, Serializable> limitRedisTemplate;
  //  @Autowired
//  private RedisTemplate redisTemplate;

  @Autowired
  private DefaultRedisScript defaultRedisScript;

  @Around("@annotation(limit)")
  public Object around(ProceedingJoinPoint point, Limit limit) {
    Object result = null;
    //Signature signature = point.getSignature();
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    // 限制访问次数
    int limitCount = limit.count();//1
    // 限制类型
    LimitType limitType = limit.limitType();
    // 资源名称，用于描述接口功能
    String name = limit.name();
    //时间范围，单位秒
    int limitPeriod = limit.period();//3

    // 获取此次请求的request
    //String ipAddr = IpUtil.getIpAddr();
    //RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes requestAttributes1 = (ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
    //ServletRequestAttributes requestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = requestAttributes1.getRequest();
    String ip = IpUtil.getIpAddr(request);

    String key;

    switch (limitType) {
      case IP:
        key = ip;
        break;
      case CUSTOMER:
        key = limit.key();  // 资源 key   key= addMenu
        break;
      default:
        key = StringUtils.upperCase(method.getName());
    }

    // 定义key参数
    List<String> keys = new Vector<>();  //线程安全的集合  多线程使用
    keys.add(limit.prefix()+"_"+ limit.key()+ key);

    // 获取Lua脚本内容
    String luaScript = buildLuaScript();

    // Reids整合Lua
    RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
    Number count =  limitRedisTemplate.execute(redisScript, keys, limitCount, limitPeriod);

    log.info("IP:{} 第 {} 次访问key为 {}，描述为 [{}] 的接口", ip, count, keys, name);

    if (count != null && count.intValue() <= limitCount) {
      try {
        return point.proceed();
      } catch (Throwable throwable) {
        throwable.printStackTrace();
      }
    } else {
      throw new RuntimeException("接口访问超出频率限制");
    }
    return result;
  }


  /**
   * 限流脚本
   * 调用的时候不超过阈值，则直接返回并执行计算器自加。
   * 编写 redis Lua 限流脚本
   */
  public String buildLuaScript() {
    StringBuilder lua = new StringBuilder();
    lua.append("local c");
    lua.append("\nc = redis.call('get',KEYS[1])");
    // 调用不超过最大值，则直接返回
    lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
    lua.append("\nreturn c;");
    lua.append("\nend");
    // 执行计算器自加
    lua.append("\nc = redis.call('incr',KEYS[1])");
    lua.append("\nif tonumber(c) == 1 then");
    // 从第一次调用开始限流，设置对应键值的过期
    lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
    lua.append("\nend");
    lua.append("\nreturn c;");
    return lua.toString();
  }
}
