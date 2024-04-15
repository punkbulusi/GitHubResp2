package com.bobo.controller;

import com.bobo.dto.UserDto;
import com.bobo.mapper.UserRoleMapper;
import com.bobo.pojo.Role;
import com.bobo.pojo.User;
import com.bobo.service.IRoleService;
import com.bobo.service.IUserService;
import com.bobo.service.impl.UserServiceImpl;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;

    @RequiresRoles(value = {"管理员","操作员",},logical = Logical.OR)
    @RequestMapping("/query")
    public String userQuery(UserDto userDto, Model model){
        PageInfo<User> pageInfo = userService.queryByPage(userDto);
        model.addAttribute("pageInfo",pageInfo);
        return "user/user";
    }

    /**
     * user的调度器
     * 当有id传入时是更新操作，需要查询用户信息和当前用户的角色信息
     * 不管有没有id传入，都需要查询可选角色信息
     * @param userId
     * @return
     */
    @RequiresRoles(value = {"管理员","操作员",},logical = Logical.OR)
    @RequestMapping("/userDispatcher")
    public String userDispatcher(Integer userId,Model model) throws Exception {
        if(userId != null && userId > 0){
            User user = userService.queryUserById(userId);
            model.addAttribute("user",user);
            List<Integer> ownerRoleIds =userService.queryUserRoleIds(userId);
            model.addAttribute("ownerRoleIds",ownerRoleIds);
        }

        List<Role> roles = roleService.query();
        model.addAttribute("roles",roles);

        return "user/updateUser";
    }

    /**
     * 前端会传递两种情况
     * 第一种；没有userId，这种情况为添加user。
     * 将user信息添加进user表，将user和role的关系添加进对应的关系表
     *
     * 第二种；有userId，这种情况更新user
     * 更新user信息，更新user和role的关系
     *
     * @return
     */
    @RequiresRoles(value = {"管理员","操作员",},logical = Logical.OR)
    @RequestMapping("/userSaveOrUpdate")
    public String userSaveOrUpdate(UserDto dto){
        if(dto.getUser() != null && dto.getUser().getUserId() ==null){
            userService.addUser(dto);
        }else{
            userService.updateUser(dto);
        }
        return "redirect:/user/query";
    }

    /**
     * 删除用户，但是这里我们删除并不是从数据库删除，因为有些数据后期可能还有用
     * 用户有一个属性为u2，我们将u2作为是否删除的标记,1代表删除
     * @param id 要删除的id
     * @return 返回查询页面
     */
    @RequiresRoles(value = {"管理员","操作员",},logical = Logical.OR)
    @RequestMapping("/deleteById")
    public String deleteById(Integer id){
        //根据ID获取user，并将u2位置改为1
        User user = userService.queryUserById(id);
        user.setU2("1");
        //准备userDto对象，因为不修改角色信息，所以不往userDto中存放RoleIds对象
        UserDto userDto = new UserDto();
        userDto.setUser(user);
        //更新user的u2
        userService.updateUser(userDto);
        return "redirect:/user/query";
    }

    @RequiresRoles(value = {"管理员","操作员",},logical = Logical.OR)
    @RequestMapping("/userNameCheck")
    @ResponseBody
    public String userNameCheck(String userName){
        User user = new User();
        user.setUserName(userName);
        List<User> query = userService.query(user);
        if(query != null && query.size() == 0){
            //返回1，代表没有重复值
            return "1";
        }
        else{
            //返回0代表有一个或多个重复值
            return "0";
        }
    }
}
