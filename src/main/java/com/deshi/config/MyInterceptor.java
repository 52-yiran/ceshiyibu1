package com.deshi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptor implements WebMvcConfigurer {

  @Autowired
  private AuthenticationInterceptor authenticationInterceptor;
  /**
   * 允许跨域
   * @param registry
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS","PATCH")
        .allowCredentials(true)
        .maxAge(3600)
        .allowedHeaders("*");

  }


  /**
   * 之定义拦截
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authenticationInterceptor)
        .addPathPatterns("/**").excludePathPatterns("/api/customerUser/login");    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
    //.excludePathPatterns("/**");
  }

  /*@Bean
  public AuthenticationInterceptor authenticationInterceptor() {
    return new AuthenticationInterceptor();
  }*/
}
