package com.jarvish.controller;

import com.jarvish.pojo.User;
import com.jarvish.result.Result;
import com.jarvish.result.ResultFactory;
import com.jarvish.service.UserService;
import com.jarvish.service.serviceImpl.UserServiceImpl;
import com.jarvish.util.JWTUtil;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
public class UserController {

    @Resource
    UserServiceImpl userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(@RequestBody User user) {

        System.out.println(user.getUsername());
        User user_query = new User(0, user.getUsername(), DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        User login = userService.login(user_query);
        if (login!=null){
            HashMap<Object, Object> data = new HashMap<>();
            data.put("uid",login.getId());
            data.put("username",login.getUsername());
            data.put("token", JWTUtil.sign(user));

            return ResultFactory.buildSuccessResult(data,"登录成功");
        }else {
            return ResultFactory.buildFailResult("登录失败");
        }
    }



}
