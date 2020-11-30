package com.ceshi.vo;

import lombok.Data;

/**
 * @Author: laowang
 * @Date: 2020/9/30
 * @Description
 *
 */

@Data
public class ValidateCodeProperties {

  /**
   * 验证码有效时间，单位秒
   */
  private Long time;
  /**
   * 验证码类型，可选值 png和 gif
   */
  private String type;
  /**
   * 图片宽度，px
   */
  private Integer width;
  /**
   * 图片高度，px
   */
  private Integer height;
  /**
   * 验证码位数
   */
  private Integer length;
  /**
   * 验证码值的类型
   * 1. 数字加字母
   * 2. 纯数字
   * 3. 纯字母
   */
  private Integer charType;
}
