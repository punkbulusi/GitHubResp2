package com.bobo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    /**
     * 跳转到主页面的servlet
     * @return
     */
    @RequestMapping(value = {"/","/home"})
    public String showMain(){
        return "main";
    }

    /**
     * 跳转到对应的页面
     * @return
     */
    @RequestMapping(value = {"/{path}"})
    public String showPage(@PathVariable String path){
        return path;
    }

    @RequestMapping(value = {"/{path}/{path2}"})
    public String showPage2(@PathVariable String path, @PathVariable String path2){
        return path+"/"+path2;
    }

}
