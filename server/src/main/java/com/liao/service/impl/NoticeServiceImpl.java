package com.liao.service.impl;

import com.liao.mapper.NoticeMapper;
import com.liao.pojo.Notice;
import com.liao.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper mapper;

    @Override
    public void add(Notice notice) {
        mapper.add(notice);
    }

    @Override
    public Notice findById(Integer id) {
        return mapper.findById(id);
    }

    @Override
    public List<Notice> list() {
        return mapper.list();
    }

    @Override
    public void delete(Integer id) {
        mapper.delete(id);
    }
}
