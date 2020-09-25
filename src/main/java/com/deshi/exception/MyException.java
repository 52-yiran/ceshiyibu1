package com.deshi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MyException {

  @ExceptionHandler(RuntimeException.class)
  public Result aa(RuntimeException e){

    log.error("异常,{}",e.getMessage());
    return new Result(400,e.getMessage());
  }

}
