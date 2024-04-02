package com.bobo.controller;


import com.bobo.pojo.Role;
import com.bobo.service.IRoleService;
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
    @RequestMapping("/query")
    public String query(Role role, Model model) throws Exception{
        List<Role> list = service.query(role);
        model.addAttribute("list",list);
        return "role/role";
    }

    /**
     * 主要用于跳转至添加用户的界面
     * @param id
     * @return
     */
    @RequestMapping("/roleDispatcher")
    public String handlePageDispatch(Integer id){
        return "role/updateRole";
    }

    /**
     * 保存或更新角色信息，但是这里只实现了保存角色信息，并没有开发更新功能
     * @param role
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(Role role) throws Exception{
        service.addRole(role);
        return "redirect:/role/query";
    }
}
