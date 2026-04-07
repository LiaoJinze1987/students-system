package com.liao.controller;

import com.liao.pojo.Clazz;
import com.liao.pojo.Result;
import com.liao.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/class")
@Validated//校验指示符
public class ClassController {

    @Autowired
    private ClassService classService;

    @PostMapping("/add")
    public Result add(@RequestBody @Validated(Clazz.Add.class) Clazz clazz) {
        Clazz check = classService.findByName(clazz.getName());
        if(check == null) {
            classService.add(clazz);
            return Result.success();
        } else {
            return Result.error("same class name");
        }
    }

    @GetMapping("/infoByName")
    public Result<Clazz> infoByName(String name) {
        Clazz clazz = classService.findByName(name);
        if(clazz == null) {
            return Result.error("no this class");
        } else {
            return Result.success(clazz);
        }
    }

    @GetMapping("/infoById")
    public Result<Clazz> infoById(Integer id) {
        Clazz clazz = classService.findById(id);
        if(clazz == null) {
            return Result.error("no this class");
        } else {
            return Result.success(clazz);
        }
    }

    @GetMapping("/list")
    public Result<List<Clazz>> list() {
        List<Clazz> list = classService.list();
        return Result.success(list == null ? Collections.emptyList() : list);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated(Clazz.Update.class) Clazz clazz) {
        classService.update(clazz);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id) {
        classService.delete(id);
        return Result.success();
    }
}
