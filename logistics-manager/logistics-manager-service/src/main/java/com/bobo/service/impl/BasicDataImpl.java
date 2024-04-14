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
    @Override
    public List<BasicData> query(BasicData basicData) {
        BasicDataExample basicDataExample = new BasicDataExample();
        if(basicData != null && basicData.getBaseId() != null){
            Integer baseId = basicData.getBaseId();
            basicDataExample.createCriteria().andBaseIdEqualTo(baseId);
        }

        return basicDataMapper.selectByExample(basicDataExample);
    }

    @Override
    public Integer addBasicData(BasicData basicData) {
        return basicDataMapper.insertSelective(basicData);
    }

    @Override
    public Integer deleteById(Integer id) {
        return basicDataMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer updateById(BasicData basicData) {
        return basicDataMapper.updateByPrimaryKey(basicData);
    }

    @Override
    public List<BasicData> queryParentId() {
        BasicDataExample basicDataExample = new BasicDataExample();
        basicDataExample.createCriteria().andParentIdIsNull();
        List<BasicData> basicDataList = basicDataMapper.selectByExample(basicDataExample);
        return basicDataList;
    }
}
