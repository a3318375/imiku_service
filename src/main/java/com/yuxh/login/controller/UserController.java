package com.yuxh.login.controller;

import com.yuxh.blog.contact.BaseJsonResult;
import com.yuxh.login.domain.User;
import com.yuxh.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/save")
    public BaseJsonResult saveUser(User user) {
        return userService.saveUser(user);
    }

    @RequestMapping("/findLatelyUser")
    public BaseJsonResult findLatelyUser() {
        return userService.findLatelyUser();
    }
}
