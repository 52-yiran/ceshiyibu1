package com.ceshi.service;

import com.ceshi.vo.ImageType;
import com.ceshi.vo.ValidateCodeProperties;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

/**
 * @Author: laowang
 * @Date: 2020/9/30
 * @Description
 */
@Data
@Service
public class ValidateCodeService {

  private ValidateCodeProperties code = new ValidateCodeProperties();

  public ValidateCodeService(){
    //ValidateCodeProperties code = new ValidateCodeProperties();
    code.setCharType(2);
    code.setTime(120L);
    code.setType("png");
    code.setWidth(130);
    code.setHeight(48);
    code.setLength(4);
  }

  public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
    HttpSession session = request.getSession();
    //String key = session.getId();

    /*ValidateCodeProperties code = new ValidateCodeProperties();
    code.setCharType(2);
    code.setTime(120L);
    code.setType("png");
    code.setWidth(130);
    code.setHeight(48);
    code.setLength(4);*/
    setHeader(response, code.getType());

    Captcha captcha = createCaptcha(code);
    // 获取生成的字符数组
    String text = captcha.text();
    System.out.println(text); //6769
    //redisService.set(MonkeyPayConstant.CODE_PREFIX  + key, StringUtils.lowerCase(captcha.text()), code.getTime());
    //captcha.out(response.getOutputStream()); // 直接返回浏览器的图片

    //String s = captcha.toBase64();// base64的图片
    //System.out.println(s);
    char[] chars = captcha.textChar();
    System.out.println(Arrays.toString(chars));
  }


  public void check(String key, String value)  {
    //Object codeInRedis = redisService.get(MonkeyPayConstant.CODE_PREFIX + key);
    if (StringUtils.isBlank(value)) {
      throw new RuntimeException("请输入验证码");
    }
    /*if (codeInRedis == null) {
      throw new RuntimeException("验证码已过期");
    }*/

    /*if (!StringUtils.equalsIgnoreCase(value, String.valueOf(codeInRedis))) {
      throw new MonkeyPayException("验证码不正确");
    }*/
  }

  private Captcha createCaptcha(ValidateCodeProperties code) {
    Captcha captcha;
    if (StringUtils.equalsIgnoreCase(code.getType(), ImageType.GIF)) {
      captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
    } else {
      captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
    }
    captcha.setCharType(code.getCharType());
    return captcha;
  }

  private void setHeader(HttpServletResponse response, String type) {
    if (StringUtils.equalsIgnoreCase(type, ImageType.GIF)) {
      response.setContentType(MediaType.IMAGE_GIF_VALUE); //"image/gif";
    } else {
      response.setContentType(MediaType.IMAGE_PNG_VALUE);
    }
    response.setHeader(HttpHeaders.PRAGMA, "No-cache"); //"Pragma";
    response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache"); //"Cache-Control";
    response.setDateHeader(HttpHeaders.EXPIRES, 0L); //"Expires";
  }
}
