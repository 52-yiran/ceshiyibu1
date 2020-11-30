package com.ceshi.util;

import com.ceshi.mapper.OtherMapper;
import com.ceshi.pojo.Other;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@EnableAsync
@Component
public class Myutil {

  @Autowired
  private OtherMapper otherMapper;

  @Async
  public void  dosome(){
    Other other = new Other();
    other.setTitle("测试的");
    other.setUsername("第一个额");
    otherMapper.insertSelective(other);
    System.out.println("other添加成功");
  }
}
