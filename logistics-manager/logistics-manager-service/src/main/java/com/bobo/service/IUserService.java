package com.bobo.service;


import com.bobo.pojo.User;

import java.util.List;

/**
 * 实现用户增，删，改，查的功能service层接口
 * 增删改，返回一个整数，确认是否正常
 * 而查返回查询的内容
 */
public interface IUserService {

    Integer addUser(User user);

    Integer deleteUser(User user);

    Integer updateUser(User user);

    List<User> query(User user);

    User queryUserById(Integer userId);

}
