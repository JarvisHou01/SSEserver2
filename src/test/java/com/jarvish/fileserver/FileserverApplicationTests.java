package com.jarvish.fileserver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jarvish.controller.FileController;
import com.jarvish.dao.FileMapper;
import com.jarvish.dao.UserMapper;
import com.jarvish.pojo.File;
import com.jarvish.pojo.User;
import com.jarvish.service.serviceImpl.FileServiceImpl;
import com.jarvish.util.JWTUtil;
import com.jarvish.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class FileserverApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    DataSource dataSource;
    @Autowired
    FileServiceImpl fileService;

    @Test
    void contextLoads() {


    }

}
