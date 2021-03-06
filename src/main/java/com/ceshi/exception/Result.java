package com.ceshi.exception;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class Result<T> implements Serializable {

  private Integer code;
  private String message;
  private T data;
  private LocalDateTime timestamp;

  public Result(Integer code, String message) {
    this.code = code;
    this.message = message;
  }


  public Result(String message, LocalDateTime timestamp) {
    this.message = message;
    this.timestamp = timestamp;
  }

  public Result(Integer code, String message, LocalDateTime timestamp) {
    this.code = code;
    this.message = message;
    this.timestamp = timestamp;
  }

  public Result(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }
}
