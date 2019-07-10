package com.eze.webservice.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //  responseBody를 모든 메소드에 적용
public class WebRestController {

    @GetMapping("/hello")   //  localhost:8080/hello
    public String hello() {
        return "hello World";   //    jSon형태로 반환
    }
}
