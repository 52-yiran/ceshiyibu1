package com.deshi.service.impl;

import com.deshi.mapper.OtherMapper;
import com.deshi.mapper.UserMapper;
import com.deshi.pojo.Other;
import com.deshi.pojo.User;
import com.deshi.service.MyService;
import com.deshi.util.Myutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class MyServiceImpl implements MyService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private Myutil myutil;

  @Autowired
  private OtherMapper otherMapper;

  @Transactional
  @Override
  public void add() {
    User user = new User();
    user.setCreated(new Date());
    user.setMoney(10d);
    user.setUsername("laowang");
    user.setPassword("123456");

    userMapper.insertSelective(user);


    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //dosome1();
    //开启一个异步
    myutil.dosome();
    //int i = 1/0; // 这里的异步开的线程  不能回调
    System.out.println("执行完");  // 第一步     执行完

  }

  @Override
  public void mm(Integer id) {
    if(id== 1){
      throw new RuntimeException("等于1的异常");
    }
  }

  @Async
  public void dosome1() {
    Other other = new Other();
    other.setTitle("测试的");
    other.setUsername("第一个额");
    otherMapper.insertSelective(other);
    System.out.println("添加一个other"); // 第二步 other 执行完
  }
}
