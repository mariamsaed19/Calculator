package com.assingment2.calc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@CrossOrigin
class controller {
    Home v=new Home();

    @GetMapping("/op")
    public String getResult(@RequestParam("op") String op,@RequestParam("num1") String num1,@RequestParam("num2") String num2){
        v.setFirst(num1);
        if(num2.length()>0) {
            v.setSecond(num2);
        }
        switch (op){
            case "/":{
                return v.division();
            }
            case "*":{
                return v.multi();
            }
            case "-" :{
                return v.sub();
            }
            case "+":{
                return v.sum();
            }
            case "sq":{
                return v.square();
            }
            case "sqr":{
                return v.sqr();
            }
            case "in":{
                return v.inverse();
            }
            case "%" :{
                return v.percent();
            }
        }
        return "";
    }

}
