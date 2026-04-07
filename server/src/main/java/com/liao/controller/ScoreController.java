package com.liao.controller;

import com.liao.pojo.Result;
import com.liao.pojo.Score;
import com.liao.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/score")
@Validated//校验指示符
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @PostMapping("/add")
    public Result add(@RequestBody @Validated(Score.Add.class) Score score) {
        scoreService.add(score);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<Score>> list(Integer studentId) {
        List<Score> list = scoreService.list(studentId);
        return Result.success(list == null ? Collections.emptyList() : list);
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id) {
        scoreService.delete(id);
        return Result.success();
    }

}
