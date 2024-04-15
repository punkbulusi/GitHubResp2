package com.bobo.service.impl;

import com.bobo.dto.CustomerDto;
import com.bobo.mapper.BasicDataMapper;
import com.bobo.mapper.CustomerMapper;
import com.bobo.mapper.UserMapper;
import com.bobo.pojo.BasicData;
import com.bobo.pojo.Customer;
import com.bobo.pojo.CustomerExample;
import com.bobo.pojo.User;
import com.bobo.service.ICustomerService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerImpl implements ICustomerService {
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    BasicDataMapper basicDataMapper;
    /**
     * 根据传入的customer是否有customerId来判断是更新还是新增
     * 有ID为更新，根据主键进行更新，更新要注意全部内容全部更新进去，不用select
     * 无ID为新增
     * @param customer
     * @return
     */
    @Override
    public Integer saveOrUpdateCustomer(Customer customer) {
        if(customer != null && customer.getCustomerId() != null){
            customerMapper.updateByPrimaryKey(customer);
        }else{
            customerMapper.insertSelective(customer);
        }

        return null;
    }

    @Override
    public Integer deleteCustomerById(Integer id) {
        return customerMapper.deleteByPrimaryKey(id);
    }

    /**
     * 和名字一样，查询所有客户
     * 但是这里返回的是customerDto，里面除了customer的基础属性，还有userName，baseName。
     * @return
     */
    @Override
    public List<CustomerDto> queryAllCustomer() throws InvocationTargetException, IllegalAccessException {
        //取出所有客户
        CustomerExample customerExample = new CustomerExample();
        List<Customer> customers = customerMapper.selectByExample(customerExample);
        //创建返回的customerDto对象
        List<CustomerDto> customerDtos = new ArrayList<>();
        //将每个customer对象存入customerDto中，同时将每个userName，baseName。查询后存入
        for(Customer customer:customers){
            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(customer,customerDto);
            //userName查出存入
            Integer userId = customer.getUserId();
            User user = userMapper.selectByPrimaryKey(userId);
            String userName = user.getUserName();
            customerDto.setUserName(userName);
            //baseName查出存入
            Integer baseId = customer.getBaseId();
            BasicData basicData = basicDataMapper.selectByPrimaryKey(baseId);
            String baseName = basicData.getBaseName();
            customerDto.setIntervalName(baseName);
            //将单个customerDto对象存入要返回的customerDtos中
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }

    @Override
    public Customer queryByCustomerId(Integer customerId) {
        return customerMapper.selectByPrimaryKey(customerId);
    }

    /**
     * 根据userId查询对应的customer
     * @param userId
     * @return
     */
    @Override
    public List<CustomerDto> queryByUserId(Integer userId) throws InvocationTargetException, IllegalAccessException {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andUserIdEqualTo(userId);
        List<Customer> customers = customerMapper.selectByExample(customerExample);
        //创建返回的customerDto对象
        List<CustomerDto> customerDtos = new ArrayList<>();
        //将每个customer对象存入customerDto中，同时将每个userName，baseName。查询后存入
        for(Customer customer:customers){
            CustomerDto customerDto = new CustomerDto();
            BeanUtils.copyProperties(customer,customerDto);
            //userName查出存入
            Integer userId1 = customer.getUserId();
            User user = userMapper.selectByPrimaryKey(userId1);
            String userName = user.getUserName();
            customerDto.setUserName(userName);
            //baseName查出存入
            Integer baseId = customer.getBaseId();
            BasicData basicData = basicDataMapper.selectByPrimaryKey(baseId);
            String baseName = basicData.getBaseName();
            customerDto.setIntervalName(baseName);
            //将单个customerDto对象存入要返回的customerDtos中
            customerDtos.add(customerDto);
        }
        return customerDtos;
    }

    /**
     * 根据userId查询对应的customerId
     * @param userId
     * @return
     */
    @Override
    public List<Integer> queryCustomerIdByUserId(Integer userId) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andUserIdEqualTo(userId);
        List<Customer> customers = customerMapper.selectByExample(customerExample);
        List<Integer> customerIdS = new ArrayList<>();
        for(Customer customer:customers){
            customerIdS.add(customer.getCustomerId());
        }
        return customerIdS;
    }

}
