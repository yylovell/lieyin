package com.yy.lieyin.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Service", description = "服务增删改查")
@RestController
@RequestMapping(value = "service", produces = "application/json;charset=utf-8")
public class ServiceController {

    @ApiOperation(value = "新增服务")
    @PostMapping(value = "queryContractFirstList")
    public Object addService(){
        return null;
    }

}
