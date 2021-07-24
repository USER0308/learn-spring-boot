package com.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @ApiOperation(value = "index")
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
