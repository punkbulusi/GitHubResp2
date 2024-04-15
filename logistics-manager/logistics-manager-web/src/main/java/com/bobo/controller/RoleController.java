package com.bobo.controller;


import com.bobo.pojo.Role;
import com.bobo.service.IRoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController  {
    @Autowired
    private IRoleService service ;

    /**
     * controller层的query功能
     * @param role 角色
     * @param model 用来向前端传输数据模型
     * @return 转向role页面
     * @throws Exception
     */
    @RequiresRoles(value = "管理员",logical = Logical.OR)
    @RequestMapping("/query")
    public String query(Role role, Model model) throws Exception{
        List<Role> list = service.query(role);
        model.addAttribute("list",list);
        return "role/role";
    }

    /**
     * 主要用于跳转至添加角色的界面,更新角色的界面
     * @param id
     * @return
     */
    @RequiresRoles(value = "管理员",logical = Logical.OR)
    @RequestMapping("/roleDispatcher")
    public String handlePageDispatch(Integer id,Model model) throws Exception {
        //这里做一次判断，判断是更新操作还是添加操作
        if(id != null){
            Role role = service.queryById(id);
            model.addAttribute("role",role);
        }
        return "role/updateRole";
    }

    /**
     * 保存或更新角色信息，根据role的id属性保存或者更新
     * @param role
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "管理员",logical = Logical.OR)
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(Role role) throws Exception{
        if(role.getRoleId() != null && role.getRoleId() > 0){
            service.updateRole(role);
        }else{
            service.addRole(role);
        }
        return "redirect:/role/query";
    }

    /**
     * 根据角色ID删除角色信息
     * @param id
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "管理员",logical = Logical.OR)
    @RequestMapping("/deleteById")
    public String deleteById(Integer id) throws Exception{
        service.deleteRole(id);
        return "redirect:/role/query";
    }
}
