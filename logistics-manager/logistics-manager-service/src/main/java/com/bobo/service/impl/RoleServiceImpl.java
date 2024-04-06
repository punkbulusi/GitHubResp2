package com.bobo.service.impl;

import com.bobo.mapper.RoleMapper;
import com.bobo.pojo.Role;
import com.bobo.pojo.RoleExample;
import com.bobo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    RoleMapper mapper;

    @Override
    public Integer addRole(Role role) throws Exception {
        return mapper.insertSelective(role);
    }

    @Override
    public Integer deleteRole(Integer roleId) throws Exception {
        return mapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public Integer updateRole(Role role) throws Exception {
        return mapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 查询角色的实现，但是这里example中并没有引入role参数中的相关数据，所以查询条件为空，可以查询所有数据。
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public List<Role> query(Role role) throws Exception {
        RoleExample example = new RoleExample();
        return mapper.selectByExample(example);
    }

    @Override
    public Role queryById(Integer roleId) throws Exception{
        return mapper.selectByPrimaryKey(roleId);
    }
}
