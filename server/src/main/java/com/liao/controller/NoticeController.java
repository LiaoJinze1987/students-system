package com.liao.controller;

import com.liao.pojo.Notice;
import com.liao.pojo.Result;
import com.liao.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/notice")
@Validated//校验指示符
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping("/add")
    public Result add(@RequestBody @Validated(Notice.Add.class) Notice notice) {
        noticeService.add(notice);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<Notice> info(Integer id) {
        Notice notice = noticeService.findById(id);
        if(notice != null) {
            return Result.success(notice);
        } else {
            return Result.error("no this notice");
        }
    }

    @GetMapping("/list")
    public Result<List<Notice>> list() {
        List<Notice> list = noticeService.list();
        return Result.success(list == null ? Collections.emptyList() : list);
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id) {
        noticeService.delete(id);
        return Result.success();
    }

}
