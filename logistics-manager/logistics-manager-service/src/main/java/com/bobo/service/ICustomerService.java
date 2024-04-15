package com.bobo.service;

import com.bobo.dto.CustomerDto;
import com.bobo.pojo.Customer;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ICustomerService {

    Integer saveOrUpdateCustomer(Customer customer);

    Integer deleteCustomerById(Integer id);

    List<CustomerDto> queryAllCustomer() throws InvocationTargetException, IllegalAccessException;

    Customer queryByCustomerId(Integer customerId);

    List<CustomerDto> queryByUserId(Integer userId) throws InvocationTargetException, IllegalAccessException;

    List<Integer> queryCustomerIdByUserId(Integer userId);
}
