package com.jarvish.service.serviceImpl;

import com.jarvish.dao.UserMapper;
import com.jarvish.pojo.User;
import com.jarvish.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(User user) {

        return userMapper.login(user);
    }
}
