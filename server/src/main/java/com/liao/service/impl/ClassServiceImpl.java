package com.liao.service.impl;

import com.liao.mapper.ClassMapper;
import com.liao.pojo.Clazz;
import com.liao.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassMapper classMapper;

    @Override
    public Clazz findByName(String className) {
        return classMapper.findByName(className);
    }

    @Override
    public Clazz findById(Integer id) {
        return classMapper.findById(id);
    }

    @Override
    public List<Clazz> list() {
        return classMapper.list();
    }

    @Override
    public void add(Clazz clazz) {
        classMapper.add(clazz);
    }

    @Override
    public void update(Clazz clazz) {
        classMapper.update(clazz);
    }

    @Override
    public void delete(Integer id) {
        classMapper.delete(id);
    }
}
