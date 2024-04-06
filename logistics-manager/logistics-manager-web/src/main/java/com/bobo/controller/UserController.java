package com.bobo.controller;

import com.bobo.pojo.User;
import com.bobo.service.IUserService;
import com.bobo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    @RequestMapping("/query")
    public String userDispatcher(User user, Model model){
        List<User> userList = userService.query(user);
        model.addAttribute("list",userList);
        return "user/user";
    }
}
