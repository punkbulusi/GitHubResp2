package com.bobo.dto;

import com.bobo.pojo.User;

import java.util.List;

/**
 * 用来传递用户数据和角色ID的对象
 */
public class UserDto extends BasePage{
    private User user;
    private List<Integer> roleIds;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
