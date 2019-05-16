package com.yy.lieyin.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2018 Shanghai Shangjia Logistics Co., Ltd. All rights reserved.
 * 全局异常处理类
 *
 * 用于全局返回json，如需返回ModelAndView请使用@ControllerAdvice
 * @author yy
 * @date 2019-04-09
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<Object>(handlerException(ex), HttpStatus.OK);
    }

    /**
     * 进入controller之后的异常捕获
     * @param e 捕获的异常
     * @return 封装的返回对象
     **/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HrsResponse handlerException(Exception e) {
        HrsResponse restResult = new HrsResponse();
        List<String> msg = new ArrayList<>();
        msg.add(e.getMessage());
        restResult.setMsg(msg);
        restResult.setCode(500);
        if (e instanceof AuthException) {// 权限token错误
            restResult.setCode(1001);
        }
        return restResult;
    }
}