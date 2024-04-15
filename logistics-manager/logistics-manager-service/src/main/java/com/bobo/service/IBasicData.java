package com.bobo.service;

import com.bobo.pojo.BasicData;
import com.bobo.pojo.BasicDataExample;

import java.util.List;

/**
 * 基础数据增删改查的接口
 */
public interface IBasicData {

    /**
     * 查询功能
     * @param basicData
     * @return
     */
    List<BasicData> query(BasicData basicData);

    /**
     * 增加功能
     * @param basicData
     * @return
     */
    Integer addBasicData(BasicData basicData);

    /**
     * 删除功能
     * @param id
     * @return
     */
    Integer deleteById(Integer id);

    /**
     * 更新功能
     * @param basicData
     * @return
     */
    Integer updateById(BasicData basicData);

    /**
     * 查询所有父类ID
     * @return
     */
    List<BasicData> queryParentId();

    List<BasicData> queryParentName(String parentName);
}
