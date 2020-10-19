package com.deshi.controller;

import com.deshi.annotation.ControllerEndpoint;
import com.deshi.annotation.Limit;
import com.deshi.pojo.Other;
import com.deshi.pojo.VO;
import com.deshi.service.MyService;
import com.deshi.service.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping
public class MyController {
  @Autowired
  private MyService myService;



  @GetMapping("/my")
  public String my(){

    myService.add();
    return "";
  }

  /**
   * 测试 从request中获取 参数
   * @param id
   * @param request
   * @return
   */
  @GetMapping("/m/{id}")
  public String MMM(@PathVariable("id") Integer id, @RequestBody Other other, HttpServletRequest request){
    /*Map<String, String[]> parameterMap = request.getParameterMap();
    System.out.println(parameterMap.toString());

    Enumeration<String> enu = request.getParameterNames();

    JSONObject jsonObject =new JSONObject();
    while (enu.hasMoreElements()){
      String s = enu.nextElement();
      jsonObject.put(s,request.getParameter(s));
    }
    System.out.println(jsonObject.toString());*/

    /*String param= null;
    try {
      BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
      StringBuffer responseStrBuilder = new StringBuffer();
      String inputStr;
      while ((inputStr = streamReader.readLine()) != null)
        responseStrBuilder.append(inputStr);

      JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
      param= jsonObject.toJSONString();
      System.out.println(param);
    } catch (Exception e) {
      e.printStackTrace();
    }*/




    StringBuffer jb = new StringBuffer();
    String line = null;
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8")) /* request.getReader()*/;
      while ((line = reader.readLine()) != null)
        jb.append(line);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(jb.toString());

    /*String param = "";
    InputStream is = null;
    InputStreamReader isr = null;
    BufferedReader br = null;
    try {
      StringBuffer sb = new StringBuffer();
      is = request.getInputStream();
      isr = new InputStreamReader(is, "UTF-8");
      br = new BufferedReader(isr);
      String s = "";
      while ((s = br.readLine()) != null) {
        sb.append(s);
      }
      param = sb.toString();
      System.out.println(param);
      param = URLDecoder.decode(param, "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (br != null) {
          br.close();
        }
        if (isr != null) {
          isr.close();
        }
        if (is != null) {
          is.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }*/



    //myService.mm(id);
    return "ww";
  }


  /**
   * 测试 从request中获取 参数
   * @param id
   * @param request
   * @return
   */
  @GetMapping("/n/{id}")
  public String MMM(@PathVariable("id") Integer id,  HttpServletRequest request){

    myService.mm(id);
    return "ww";
  }


  @GetMapping("/getAll")
  @ControllerEndpoint(operation="你是大锤",exceptionMessage = "确实是的")
  public String getAll(@RequestBody VO vo){

    System.out.println("执行完了");
    System.out.println(vo);
    return "";

  }

  @GetMapping("/xl")
  @Limit(name = "新增菜单/按钮", key = "addMenu", prefix = "menu", period = 3, count = 1) // 限流的
  public String xl(){

    System.out.println("执行完了1111");

    return "ok";

  }

  @Autowired
  private ValidateCodeService validateCodeService;

  @GetMapping("/captcha")
  @ResponseBody
  public void captcha(HttpServletRequest request, HttpServletResponse response) {
    try {
      validateCodeService.create(request, response);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
