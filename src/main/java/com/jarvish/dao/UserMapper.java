package com.jarvish.dao;


import com.jarvish.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    User login(User user);

    int add(User user);


}
