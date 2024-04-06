package com.bobo.service.impl;

import com.bobo.mapper.UserMapper;
import com.bobo.pojo.User;
import com.bobo.pojo.UserExample;
import com.bobo.service.IRoleService;
import com.bobo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper mapper;

    /**
     * 在mapper中有insert 和 insertSelective ，
     * 这两个的主要区别在于处理null值的方式：
     * insert 会插入所有字段，无论值是否为null；
     * 而 insertSelective 则只会插入非null的字段。
     * 最佳实践还是使用insertSelective。
     * @param user
     * @return
     */
    @Override
    public Integer addUser(User user) {
        return mapper.insertSelective(user);
    }

    @Override
    public Integer deleteUser(User user) {
        return mapper.deleteByPrimaryKey(user.getUserId());
    }

    @Override
    public Integer updateUser(User user) {
        return mapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 这里查询是直接查询全部用户
     * @param user
     * @return
     */
    @Override
    public List<User> query(User user) {
        UserExample userExample = new UserExample();
        return mapper.selectByExample(userExample);
    }

    @Override
    public User queryUserById(Integer userId) {
        return mapper.selectByPrimaryKey(userId);
    }
}
