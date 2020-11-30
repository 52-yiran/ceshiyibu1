package com.ceshi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import tk.mybatis.spring.annotation.MapperScan;


@MapperScan("com.ceshi.mapper")
@SpringBootApplication
public class MyApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyApplication.class,args);
  }

  @Bean
  public DefaultRedisScript<Number> defaultRedisScript() {
    DefaultRedisScript<Number> redisScript = new DefaultRedisScript<>();
    //读取 lua 脚本
    redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("limit.lua")));
    redisScript.setResultType(Number.class);
    return redisScript;
  }

}
