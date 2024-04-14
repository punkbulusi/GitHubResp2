package com.bobo.realm;

import com.bobo.pojo.Role;
import com.bobo.pojo.User;
import com.bobo.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;


    /**
     * 认证的方法（获取授权信息）
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User)principalCollection.getPrimaryPrincipal();
        List<Role> roleList = userService.queryUserHaveRoles(user);
        try{
            if(roleList != null && roleList.size() > 0){
                SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
                for(Role role : roleList){
                    simpleAuthorizationInfo.addRole(role.getRoleName());
                }
                return simpleAuthorizationInfo;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 授权方法（获取身份验证信息）
     * 判断这个用户名在数据库里面是不是有且只有一个
     * 成立说明账号有且只有一个，做密码验证返回验证结果
     * 不成立说明账号没有或者不止一个，返回null
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //判断这个用户名在数据库里面是不是有且只有一个
        String username = token.getUsername();
        User user = userService.login(username);
        if(user != null){//成立说明账号有且只有一个，做密码验证返回验证结果
            String salty = user.getU1();
            return new SimpleAuthenticationInfo(user,user.getPassword(),new SimpleByteSource(salty),"myRealm");
        }else{//不成立说明账号没有或者不止一个
            return null;
        }
    }
}
