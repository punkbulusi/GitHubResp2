package com.bobo.service;


import com.bobo.dto.UserDto;
import com.bobo.pojo.Role;
import com.bobo.pojo.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 实现用户增，删，改，查的功能service层接口
 * 增删改，返回一个整数，确认是否正常
 * 而查返回查询的内容
 */
public interface IUserService {

    Integer addUser(UserDto dto);

    Integer deleteUser(User user);

    Integer updateUser(UserDto userDto);

    List<User> query(User user);

    List<User> query();

    User queryUserById(Integer userId);

    List<Integer> queryUserRoleIds(Integer RoleId);

    PageInfo<User> queryByPage(UserDto userDto);

    User login(String userName);

    public List<Role> queryUserHaveRoles(User user);
}
