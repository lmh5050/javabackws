package com.example.testtoy.contorller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloWorldController {

    @GetMapping(value = "/")
    public String doGetHelloWorld() {
        return "Hello World";
    }

    @GetMapping(value = "/demo")
    public String doGetHelloWorldDemo() {
        return "Hello World (Demo)";
    }

    @GetMapping("/logMessage")
    public String logMessage() {
        System.out.println("Button clicked!");  // 콘솔에 메시지 출력
        return "Message logged to console";
    }


}
