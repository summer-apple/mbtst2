package com.lanxi.lanxiproject.action;

import com.lanxi.lanxiproject.entity.User;
import com.lanxi.lanxiproject.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by summer on 16/6/7.
 */
@RestController
@RequestMapping("/user")
public class UserAction {

    @Autowired
    private IUserService userService;


    @RequestMapping("getUserById")
    public User getUserById(int id){
        return userService.getUserById(id);
    }


}
