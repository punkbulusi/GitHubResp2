package com.bobo.service;


import com.bobo.pojo.Role;

import java.util.List;

/**
 * 角色查询的接口
 * 具体接口为；增 删 改 查
 */
public interface IRoleService {
    /**
     * 实现增加角色功能
     * @param role
     * @return
     */
    Integer addRole(Role role) throws Exception;

    /**
     * 删除角色功能
     * @param roleId
     * @return
     */
    Integer deleteRole(Integer roleId) throws Exception;

    /**
     * 修改角色信息功能
     * @param role
     * @return
     */
    Integer updateRole(Role role) throws Exception;

    /**
     * 查询角色功能
     * @param role
     * @return
     */
    List<Role> query(Role role) throws Exception;

    /**
     * 查询角色根据角色ID
     * @param roleId
     * @return
     */
    Role queryById(Integer roleId)throws Exception;
}
