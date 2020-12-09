package com.ceshi.task;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Author: laowang
 * @Date: 2020/11/11
 * @Description
 */
@Slf4j
@Component
@EnableScheduling
public class TimerTask {

  //@Scheduled(cron = "0 0/1 * * * ?")// 1分钟执行一次 //
  //@Scheduled(cron = "*/5 * * * * ?")//第一次执行也是在5秒后 每隔5秒执行一次 cron = "*/5 * * * * ?"
  @Scheduled(cron = "0/5 * * * * ?")// 每隔5秒执行一次 cron = "*/5 * * * * ?"
  public void schedulerTask1() {
    System.out.println(LocalDateTime.now());

  }



}
