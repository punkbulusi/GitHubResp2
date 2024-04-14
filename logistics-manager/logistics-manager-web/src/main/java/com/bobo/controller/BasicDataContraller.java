package com.bobo.controller;

import com.bobo.pojo.BasicData;
import com.bobo.service.IBasicData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/basicData")
public class BasicDataContraller {
    @Autowired
    IBasicData basicDataService;

    /**
     * 实现查询功能，目前只实现无条件全部查询
     * @param model
     * @return
     */
    @RequestMapping("/query")
    public String query(Model model){
        List<BasicData> query = basicDataService.query(new BasicData());
        model.addAttribute("basicDatas",query);
        return "basicData/basicData";
    }

    /**
     * 基础数据的调度员，根据前端是否传入ID来转向更新或者修改
     * 不管更新或者修改都要查询父类ID有什么，或者当前的父类ID是多少
     * @return
     */
    @RequestMapping("/basicDataDispatcher")
    public String basicDataDispatcher(Integer baseId,Model model){
        if(baseId != null){ //不等于null的时候为更新,查询出对应的数据内容返回
            BasicData basicData = new BasicData();
            basicData.setBaseId(baseId);
            List<BasicData> query = basicDataService.query(basicData);
            if(query != null && query.size() == 1){
                model.addAttribute("basicData",query.get(0));
            }
        }
        //将所有父类查询出来传入前端
        List<BasicData> parentBasicDatas = basicDataService.queryParentId();
        model.addAttribute("parentBasicDatas",parentBasicDatas);
        return "basicData/updateBasicData";
    }

    /**
     * 根据传入的basicData有没有id进行判断
     * 有ID为更新
     * 没ID为新增
     * @param basicData
     * @return
     */
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(BasicData basicData){
        if(basicData != null && basicData.getBaseId() != null){
            basicDataService.updateById(basicData);
        }else {
            basicDataService.addBasicData(basicData);
        }
        return "redirect:/basicData/query";
    }
    @RequestMapping("/deleteById")
    public String deleteById(Integer id){
        if(id != null){
            basicDataService.deleteById(id);
        }
        return "redirect:/basicData/query";
    }
}
