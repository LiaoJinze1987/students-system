package com.liao.service;

import com.liao.pojo.Score;

import java.util.List;

public interface ScoreService {
    //添加成绩
    void add(Score score);
    //某个学生的所有成绩
    List<Score> list(Integer studentId);
    //删除某次成绩
    void delete(Integer id);
}
