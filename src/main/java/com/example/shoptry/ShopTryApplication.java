package com.example.shoptry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ShopTryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopTryApplication.class, args);
    }

//    @GetMapping(value="/")
//    public String HelloWorld(){
//        return "Hello World";
//    }
}
