//package com.ceshi.util;
//
//
//import com.google.common.collect.ImmutableList;
//import io.lettuce.core.Limit;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.script.DefaultRedisScript;
//import org.springframework.data.redis.core.script.RedisScript;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.io.Serializable;
//import java.lang.reflect.Method;
///**
// * * 限流切面实现 *
// */
//@Aspect
//@Configuration
//public class CurrentAspect{
//    private static final Logger logger=LoggerFactory.getLogger(CurrentAspect.class);
//    private static final String UNKNOWN="unknown";
//
//  @Resource
//  private RedisTemplate<String, Object> redisTemplate;
//
//    /*@Autowired
//    public CurrentAspect(RedisTemplate limitRedisTemplate){
//      this.RedisTemplate=limitRedisTemplate;
//    }*/
//
//
//  /** * 配置切面 * */
//  public Object interceptor(ProceedingJoinPoint pjp){
//    MethodSignature signature=(MethodSignature)pjp.getSignature();
//    Method method=signature.getMethod();
//    Current CurrentAnnotation=method.getAnnotation(Current.class);
//    LtType ltTypeType=CurrentAnnotation.LtType;
//    String name=ltTypeType.name;
//
//    String key;
//    int currentPeriod= CurrentAnnotation.period;
//    int currentCount= CurrentAnnotation.counts;
//    /** * 根据限流类型获取不同的key ,如果不传我们会以方法名作为key */
//    switch(ltTypeType){
//      case IP:
//        key=IpUtil.getIpAddr();
//        break;
//      case KEY:
//        key=CurrentAnnotation.keys;
//        break;
//      default:key=StringUtils.upperCase(method.getName);
//    }
//
//    ImmutableList keys=ImmutableList.of(StringUtils.join(CurrentAnnotation.prefixs,key));
//
//    String lua= sendLuaScript();
//
//    RedisScript redisScript = new DefaultRedisScript<>(lua,Number.class);
//    //Number count = redisTemplate.execute(redisScript,keys,currentCount,currentPeriod);
//    Number count = (Number) redisTemplate.execute(redisScript,keys,currentCount,currentPeriod);
//
//    logger.info("Access try count is {} for name={} and key = {}",count,name,key);
//    if(count!=null&&count.intValue<=currentCount){
//      return pjp.proceed;
//    }else{
//      throw new RuntimeException("不好意思，你进黑名单了");
//    }
//
//  }
//
//
//  /*** * 编写 redis Lua 限流脚本 * @return */
//  private String sendLuaScript(){
//    StringBuilder lua=new StringBuilder();
//    lua.append("local c");
//    lua.append("c = redis.call('get',KEYS[1])");
//    // 调用不超过最大值，则直接返回
//    lua.append("if c and tonumber(c) > tonumber(ARGV[1]) then");
//    lua.append("return c;");
//    lua.append("end");
//    // 执行计算器自加
//    lua.append("c = redis.call('incr',KEYS[1])");
//    lua.append("if tonumber(c) == 1 then");
//    // 从第一次调用开始限流，设置对应键值的过期
//    lua.append("redis.call('expire',KEYS[1],ARGV[2])");
//    lua.append("end");
//    lua.append("return c;");
//    return lua.toString();
//  }
//
//}
