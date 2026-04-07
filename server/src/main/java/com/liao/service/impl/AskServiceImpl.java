package com.liao.service.impl;

import com.liao.mapper.AskMapper;
import com.liao.pojo.Ask;
import com.liao.service.AskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AskServiceImpl implements AskService {

    @Autowired
    private AskMapper mapper;

    @Override
    public void add(Ask ask) {
        mapper.add(ask);
    }

    @Override
    public List<Ask> list(Integer masterId) {
        return mapper.list(masterId);
    }

    @Override
    public Ask info(Integer id) {
        return mapper.info(id);
    }

    @Override
    public void delete(Integer id) {
        mapper.delete(id);
    }

    @Override
    public void update(Integer id, Integer status) {
        mapper.update(id, status);
    }
}
