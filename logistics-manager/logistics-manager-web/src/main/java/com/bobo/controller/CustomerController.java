package com.bobo.controller;

import com.bobo.common.constant;
import com.bobo.dto.CustomerDto;
import com.bobo.pojo.BasicData;
import com.bobo.pojo.Customer;
import com.bobo.pojo.Role;
import com.bobo.pojo.User;
import com.bobo.service.IBasicData;
import com.bobo.service.ICustomerService;
import com.bobo.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IBasicData basicDataService;


    @RequestMapping("/saveOrUpdate")
    @RequiresRoles(value = {"管理员","业务员","操作员"},logical = Logical.OR)
    public String saveOrUpdate(Customer customer, HttpServletResponse response) throws IOException {
        customerService.saveOrUpdateCustomer(customer);
        return "customer/success";
    }

    /**
     * 跳转至添加用户的controller
     * 需要在model中返回roleSalemanS（业务员的user，user中role为业务员的）
     * 需要在model中返回intervals（常用区间的basicData,basicData中parentId为常用区间的baseId的）
     * @param
     * @return
     */
    @RequestMapping("/addCustomer")
    @RequiresRoles(value = {"管理员","业务员","操作员"},logical = Logical.OR)

    public String addCustomer(Model model){
        List<User> roleSalemanS = userService.queryByRoleName(constant.ROLE_SALEMAN);
        model.addAttribute("roleSalemanS",roleSalemanS);
        List<BasicData> intervals = basicDataService.queryParentName(constant.BASIC_COMMON_INTERVAL);
        model.addAttribute("intervals",intervals);
        return "customer/updateCustomer";
    }

    /**
     * 更新Customer，传入customerId
     * 需要向model中返回customerId的customer
     * roleSalemanS（业务员的user，user中role为业务员的）
     * intervals（常用区间的basicData,basicData中parentId为常用区间的baseId的）
     * @param customerId
     * @return
     */
    @RequestMapping("/updateCustomer")
    @RequiresRoles(value = {"管理员","业务员","操作员"},logical = Logical.OR)

    public String updateCustomer(Integer customerId,Model model){
        Customer customer = customerService.queryByCustomerId(customerId);
        model.addAttribute("customer",customer);
        List<User> roleSalemanS = userService.queryByRoleName(constant.ROLE_SALEMAN);
        model.addAttribute("roleSalemanS",roleSalemanS);
        List<BasicData> intervals = basicDataService.queryParentName(constant.BASIC_COMMON_INTERVAL);
        model.addAttribute("intervals",intervals);
        return "customer/updateCustomer";
    }

    /**
     * 根据当前登录的用户的角色来确认查询到哪些客户
     * 管理员和操作员可以查询到全部客户
     * 业务员只能查询到 自己的客户
     *向model中要返回查询到的客户，和
     * @return
     */
    @RequestMapping("/query")
    @RequiresRoles(value = {"管理员","业务员","操作员"},logical = Logical.OR)
    public String queryCustomer(Model model) throws InvocationTargetException, IllegalAccessException {
        //查询当前登录的用户的角色信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Role> roles = userService.queryUserHaveRoles(user);
        //将所有角色信息存入roleNameS
        List<String> roleNameS = new ArrayList<>();
        for(Role role : roles){
            roleNameS.add(role.getRoleName());
        }
        //如果是管理员或操作员，返回全部客户
        if(roleNameS.contains("管理员") || roleNameS.contains("操作员")){
            List<CustomerDto> customerDtoList = customerService.queryAllCustomer();
            model.addAttribute("customerDtoList",customerDtoList);
            return "customer/customer";
        }else if(roleNameS.contains("业务员")){//如果是业务员，只返回业务员管理的客户
            List<CustomerDto> customerDtoList = customerService.queryByUserId(user.getUserId());
            model.addAttribute("customerDtoList",customerDtoList);
            return "customer/customer";
        }else{
            return "roleError";
        }
    }

    /**
     * 删除用户的操作，该操作只能 管理员，操作员，业务员 进行
     * 其中；管理员和操作员能删除全部客户
     * 业务员只能删除他管理的客户
     * @param customerId 传进来的客户ID必须是已经经过校验没有生成订单的客户（在后续的代码中没有校验规则）
     * @return
     */
    @RequestMapping("/delete")
    @RequiresRoles(value = {"管理员","操作员","业务员"},logical = Logical.OR)
    public String deleteCustomerByCustomerId(Integer customerId){
        //查询当前登录的用户的角色信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Role> roles = userService.queryUserHaveRoles(user);
        //将所有角色信息存入roleNameS
        List<String> roleNameS = new ArrayList<>();
        for(Role role : roles){
            roleNameS.add(role.getRoleName());
        }
        //如果是管理员或操作员，可以任意进行删除操作
        if(roleNameS.contains("管理员") || roleNameS.contains("操作员")){
            customerService.deleteCustomerById(customerId);
        }else if(roleNameS.contains("业务员")){//如果是业务员，只删除业务员管理的客户
            Integer userId = user.getUserId();
            List<Integer> customerIdS = customerService.queryCustomerIdByUserId(userId);
            if(customerIdS.contains(customerId)){
                customerService.deleteCustomerById(customerId);
            }
        }
        return "redirect:/customer/query";
    }

    /**
     * 检查对应用户ID的用户是否有orderId,如果有的话返回1，没有(null)返回0
     * @param customerId
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkCustomer")
    @RequiresRoles(value = {"管理员","操作员","业务员"},logical = Logical.OR)
    public String checkCustomer(Integer customerId){
        Customer customer = customerService.queryByCustomerId(customerId);
        Integer orderId = customer.getOrderId();
        if(orderId == null ){//没有订单号
            return "0";
        }else{//表示有订单号
            return "1";
        }
    }
}
