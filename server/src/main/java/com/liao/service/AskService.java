package com.liao.service;

import com.liao.pojo.Ask;

import java.util.List;

public interface AskService {

    //添加请假
    void add(Ask ask);
    //获取担任老师对应请假列表
    List<Ask> list(Integer masterId);
    //查找对应请假详情
    Ask info(Integer id);
    //删除请假
    void delete(Integer id);
    //老师审批请假
    void update(Integer id, Integer status);
}
