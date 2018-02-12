package com.xidian.naivechain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LvLiuWei
 * @date 2018/2/12.
 */
@Controller("hello")
public class HelloController {

    @RequestMapping("index")
    public String hello() {
        return "index";
    }
}
