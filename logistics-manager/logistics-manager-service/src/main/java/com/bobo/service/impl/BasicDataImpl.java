package com.bobo.service.impl;

import com.bobo.mapper.BasicDataMapper;
import com.bobo.pojo.BasicData;
import com.bobo.pojo.BasicDataExample;
import com.bobo.service.IBasicData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasicDataImpl implements IBasicData {
    @Autowired
    BasicDataMapper basicDataMapper;

    /**
     * 根据传入的basicData进行查询
     * 目前实现了根据传入的basicData的basicDataId进行查询
     * @param basicData
     * @return
     */
    @Override
    public List<BasicData> query(BasicData basicData) {
        BasicDataExample basicDataExample = new BasicDataExample();
        if(basicData != null && basicData.getBaseId() != null){
            Integer baseId = basicData.getBaseId();
            basicDataExample.createCriteria().andBaseIdEqualTo(baseId);
        }

        return basicDataMapper.selectByExample(basicDataExample);
    }

    /**
     * 增加basicData
     * @param basicData
     * @return
     */
    @Override
    public Integer addBasicData(BasicData basicData) {
        return basicDataMapper.insertSelective(basicData);
    }

    /**
     * 根据Id真实删除对应基础数据
     * @param id
     * @return
     */
    @Override
    public Integer deleteById(Integer id) {
        return basicDataMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据传入的basicData的primaryKey更新对应的basicData
     * @param basicData
     * @return
     */
    @Override
    public Integer updateById(BasicData basicData) {
        return basicDataMapper.updateByPrimaryKey(basicData);
    }

    /**
     * 查询全部父类的基础数据对象
     * @return
     */
    @Override
    public List<BasicData> queryParentId() {
        BasicDataExample basicDataExample = new BasicDataExample();
        basicDataExample.createCriteria().andParentIdIsNull();
        List<BasicData> basicDataList = basicDataMapper.selectByExample(basicDataExample);
        return basicDataList;
    }

    /**
     * 查询基础数据对象根据父类的名称
     * @param parentName 父类名称
     * @return 所有属于该父类名称的基础数据对象
     */
    @Override
    public List<BasicData> queryParentName(String parentName) {
        //查询父类名称对应的ID
        BasicDataExample basicDataExample = new BasicDataExample();
        basicDataExample.createCriteria().andBaseNameEqualTo(parentName);
        List<BasicData> parentBasicDatas = basicDataMapper.selectByExample(basicDataExample);
        if(parentBasicDatas != null && parentBasicDatas.size() == 1){
            BasicData basicData = parentBasicDatas.get(0);
            Integer parentId = basicData.getBaseId();
            //查询父类ID对应的全部子类对象
            BasicDataExample basicDataExample1 = new BasicDataExample();
            basicDataExample1.createCriteria().andParentIdEqualTo(parentId);
            List<BasicData> sonBasicData = basicDataMapper.selectByExample(basicDataExample1);
            return sonBasicData;
        }
        return null;
    }
}
