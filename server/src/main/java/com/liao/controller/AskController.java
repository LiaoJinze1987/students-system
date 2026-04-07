package com.liao.controller;

import com.liao.pojo.Ask;
import com.liao.pojo.Result;
import com.liao.service.AskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ask")
@Validated//校验指示符
public class AskController {

    @Autowired
    private AskService askService;

    @PostMapping("/add")
    public Result add(@RequestBody @Validated(Ask.Add.class) Ask ask) {
        askService.add(ask);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<Ask>> list(Integer masterId) {
        List<Ask> list = askService.list(masterId);
        return Result.success(list == null ? Collections.emptyList() : list);
    }

    @GetMapping("/info")
    public Result<Ask> info(Integer id) {
        Ask ask = askService.info(id);
        if(ask != null) {
            return Result.success(ask);
        } else {
            return Result.error("no this ask");
        }
    }

    @PutMapping("/update")
    public Result update(Integer id, Integer status) {
        askService.update(id, status);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id) {
        askService.delete(id);
        return Result.success();
    }
}
