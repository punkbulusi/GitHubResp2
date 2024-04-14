package com.bobo.service.impl;

import com.bobo.dto.UserDto;
import com.bobo.mapper.RoleMapper;
import com.bobo.mapper.UserMapper;
import com.bobo.mapper.UserRoleMapper;
import com.bobo.pojo.*;
import com.bobo.service.IRoleService;
import com.bobo.service.IUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper mapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    RoleMapper roleMapper;

    /**
     * 在mapper中有insert 和 insertSelective ，
     * 这两个的主要区别在于处理null值的方式：
     * insert 会插入所有字段，无论值是否为null；
     * 而 insertSelective 则只会插入非null的字段。
     * 最佳实践还是使用insertSelective。
     * @param dto
     * @return
     */
    @Override
    public Integer addUser(UserDto dto) {

        //获取user
        User user = dto.getUser();
        //将密码通过md5+salt值进行存入user
        String salt = UUID.randomUUID().toString();
        Md5Hash passwordHash = new Md5Hash(user.getPassword(),salt,1024);
        user.setPassword(passwordHash.toString());
        user.setU1(salt);
        //将用户信息存入数据库
        int i = mapper.insertSelective(user);
        //这里将角色ID和当前用户ID插入角色用户表
        for(Integer roleId:dto.getRoleIds()){
            UserRoleKey userRoleKey = new UserRoleKey();
            userRoleKey.setRoleId(roleId);
            userRoleKey.setUserId(user.getUserId());
            userRoleMapper.insertSelective(userRoleKey);
        }
        return i;
    }

    @Override
    public Integer deleteUser(User user) {
        return mapper.deleteByPrimaryKey(user.getUserId());
    }

    /**
     * 更新的时候分为两步，先更新用户表
     * 再更新用户角色关系表(更新角色关系表要先删除，再添加)
     * @param userDto
     * @return
     */
    @Override
    public Integer updateUser(UserDto userDto) {
        //更新用户表
        User user = userDto.getUser();
        Integer userId = user.getUserId();
        mapper.updateByPrimaryKeySelective(user);
        //判断是否需要修改角色信息
        if(userDto.getRoleIds() != null && userDto.getRoleIds().size() > 0) {
            //删除当前用户ID相关的userRole关系数据
            UserRoleExample userRoleExample = new UserRoleExample();
            userRoleExample.createCriteria().andUserIdEqualTo(userId);
            userRoleMapper.deleteByExample(userRoleExample);
            //添加
            List<Integer> roleIds = userDto.getRoleIds();
            if (roleIds != null && roleIds.size() > 0) {
                for (Integer roleId : userDto.getRoleIds()) {
                    UserRoleKey userRoleKey = new UserRoleKey();
                    userRoleKey.setUserId(userId);
                    userRoleKey.setRoleId(roleId);
                    userRoleMapper.insertSelective(userRoleKey);
                }
            }
        }

        return 1;
    }

    /**
     * 这里查询是直接查询全部用户,由于U2为1的是被删除的用户，为0的是正常用户，所以要进行条件匹配
     * @param user
     * @return
     */
    @Override
    public List<User> query(User user) {
        UserExample userExample = new UserExample();
        //这里是确认是否能将姓名作为条件进行查询
        if(user.getUserName() != null){
            userExample.createCriteria().andUserNameEqualTo(user.getUserName());
        }
        //这里是确认用户U2属性，确认是否被删除
        userExample.createCriteria().andU2NotEqualTo("1");
        return mapper.selectByExample(userExample);
    }

    /**
     * 这里查询是直接查询全部用户,由于U2为1的是被删除的用户，为0的是正常用户，所以要进行条件匹配
     * @param
     * @return
     */
    @Override
    public List<User> query() {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andU2NotEqualTo("1");
        return mapper.selectByExample(userExample);
    }

    @Override
    public User queryUserById(Integer userId) {
        return mapper.selectByPrimaryKey(userId);
    }

    /**
     * 根据用户ID查询当前用户ID对应角色ID
     * @param RoleId
     * @return
     */
    @Override
    public List<Integer> queryUserRoleIds(Integer RoleId) {
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria().andUserIdEqualTo(RoleId);
        List<UserRoleKey> userRoleKeys = userRoleMapper.selectByExample(userRoleExample);
        List<Integer> roledIds = new ArrayList<>();
        for(UserRoleKey userRoleKey:userRoleKeys){
            roledIds.add(userRoleKey.getRoleId());
        }
        return roledIds;
    }


    /**
     * 这里是将查询逻辑上加上了分页逻辑，同时返回的结果也有分页的相关信息
     * @param userDto 这里userDto里面有当前页和每页的数量
     * @return 返回pageInfo对象，里面包含具体页数，每页数量，查询的结果
     */
    @Override
    public PageInfo<User> queryByPage(UserDto userDto) {
        //在PageHelper开启后的第一条sql语句会被加入分页逻辑
        PageHelper.startPage(userDto.getPageNum(),userDto.getPageSize());
        List<User> list = this.query();
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 登录的时候做账号数量验证，如果账号只有一个，返回该用户的相关信息，如果
     * 账号没有或者不止一个则返回null
     * @param userName 前端传入需要查询的账号名称
     * @return 返回对应的用户或者null
     */
    @Override
    public User login(String userName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserNameEqualTo(userName);
        List<User> users = mapper.selectByExample(userExample);
        if(users != null && users.size() ==1){
            return users.get(0);
        }else{
            return null;
        }
    }

    /**
     * 根据user查询具有的role
     * 如果存在返回roleList
     * 不存在返回null
     * @param user
     * @return
     */
    public List<Role> queryUserHaveRoles(User user){
        UserRoleExample userRoleExample = new UserRoleExample();
        userRoleExample.createCriteria().andUserIdEqualTo(user.getUserId());
        List<UserRoleKey> userRoleKeys = userRoleMapper.selectByExample(userRoleExample);
        if(userRoleKeys != null && userRoleKeys.size() > 0){
            List<Role> roleList = new ArrayList<>();
            for(UserRoleKey userRoleKey : userRoleKeys){
                Role role = roleMapper.selectByPrimaryKey(userRoleKey.getRoleId());
                roleList.add(role);
            }
            return roleList;
        }
        return null;
    }
}
