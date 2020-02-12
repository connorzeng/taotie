package com.connor.taotie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 备注: 可以使用@RestController代替(@Controller + @ResponseBody)
 */
@Controller
@RequestMapping("/pioneer")
public class PioneerController {

    @ResponseBody
    @RequestMapping("/hello")
    public String sayHello() {
        return "hello";
    }
}
