package com.msg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }
}
