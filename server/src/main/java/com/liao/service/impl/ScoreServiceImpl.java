package com.liao.service.impl;

import com.liao.mapper.ScoreMapper;
import com.liao.pojo.Score;
import com.liao.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper mapper;

    @Override
    public void add(Score score) {
        mapper.add(score);
    }

    @Override
    public List<Score> list(Integer studentId) {
        return mapper.list(studentId);
    }

    @Override
    public void delete(Integer id) {
        mapper.delete(id);
    }
}
