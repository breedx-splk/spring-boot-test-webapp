package com.splunk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controllers {

    @GetMapping("/greeting")
    public String hello(){
        return "hello.";
    }

}
