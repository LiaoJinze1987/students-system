package com.liao.service;

import com.liao.pojo.Clazz;

import java.util.List;

public interface ClassService {

    //根据名称查找
    Clazz findByName(String className);
    //根据 id 查找
    Clazz findById(Integer id);
    //班级列表
    List<Clazz> list();
    //添加班级
    void add(Clazz clazz);
    //修改信息
    void update(Clazz clazz);
    //删除班级
    void delete(Integer id);
}
