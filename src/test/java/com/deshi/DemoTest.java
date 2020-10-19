package com.deshi;

import com.deshi.util.AddressUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: laowang
 * @Date: 2020/9/30
 * @Description
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoTest {

  @Test
  public void test() throws IOException {
    // 读取resources 路径下的资源
    Resource resource = new ClassPathResource("/ip2region/ip2region.db");

    InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream(),"ASCII");
    BufferedReader br = new BufferedReader(inputStreamReader);
    Stream<String> lines = br.lines();

    String collect = lines.collect(Collectors.joining("\n"));//  \n是换行符，
    System.out.println(collect);// 打印的是换行的  //
    // a
    // b
  }

  @Test
  public void test2(){
    /*String cityInfo = AddressUtil.getAddress("134.122.145.196");// 美国|0|0|0|0
    System.out.println(cityInfo);*/

    /*String cityInfo = AddressUtil.getAddress("106.35.112.88");// 美国|0|0|0|0
    System.out.println(cityInfo); //中国|华北|内蒙古自治区|鄂尔多斯市|电信*/


    String cityInfo = AddressUtil.getAddress("136.27.231.86");// 美国|0|0|0|0
    System.out.println(cityInfo); //中国|华北|内蒙古自治区|鄂尔多斯市|电信

  }
}
