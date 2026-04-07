package com.liao.service;

import com.liao.pojo.Notice;

import java.util.List;

public interface NoticeService {

    //添加通知
    void add(Notice notice);
    //查找通知
    Notice findById(Integer id);
    //通知列表
    List<Notice> list();
    //删除通知
    void delete(Integer id);
}
