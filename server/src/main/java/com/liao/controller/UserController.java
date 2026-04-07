package com.liao.controller;

import com.liao.pojo.Result;
import com.liao.pojo.User;
import com.liao.service.UserService;
import com.liao.utils.BCryptUtils;
import com.liao.utils.JWTUtils;
import com.liao.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Validated//校验指示符
public class UserController {

    @Autowired
    private UserService userService;
    //@Pattern(regexp = "^\\S{5,16}$")，正则表示5-16位非空字符，参数校验

    @PostMapping("/register")
    public Result register(@RequestBody @Validated(User.Add.class) User user) {
        //查询用户
        User check = userService.findByNameAndClass(user.getUsername(), user.getClazz());
        if(check == null) {
            //注册
            userService.register(user);
            return Result.success();
        } else {
            return Result.error("same register name");
        }
    }

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody @Validated(User.Login.class) User user) {
        User loginUser = userService.findByNameAndClass(user.getUsername(), user.getClazz());
        if(loginUser == null) {
            return Result.error("username is wrong!");
        }
        boolean match = BCryptUtils.matches(user.getPassword(), loginUser.getPassword());
        if(match) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            Map<String, Object> data = new HashMap<>();
            String token = JWTUtils.genToken(claims);
            //delete password
            loginUser.setPassword("");
            data.put("token", token);
            data.put("user", loginUser);
            return Result.success(data);
        } else {
            return Result.error("password is wrong!");
        }
    }

    @GetMapping("/master")
    public Result<User> master(String clazz) {
        User master = userService.findMasterByClass(clazz);
        if(master != null) {
            return Result.success(master);
        } else {
            return Result.error("no class master");
        }
    }

    @GetMapping("/classStudents")
    public Result<List<User>> classStudents(String clazz) {
        List<User> list = userService.findClassStudents(clazz);
        if(list != null) {
            return Result.success(list);
        } else {
            return Result.error("no class students");
        }
    }

    @GetMapping("/allStudents")
    public Result<List<User>> allStudents() {
        List<User> list = userService.findAllStudents();
        if(list != null) {
            return Result.success(list);
        } else {
            return Result.error("no all students");
        }
    }

    @GetMapping("/allTeachers")
    public Result<List<User>> allTeachers() {
        List<User> list = userService.findAllTeachers();
        if(list != null) {
            return Result.success(list);
        } else {
            return Result.error("no all teachers");
        }
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(Integer userId) {
        //从ThreadLocal 保存的数据中取出登录信息
        //Map<String, Object> claims = ThreadLocalUtils.get();
        //int userId = (Integer) claims.get("id");
        //直接传userId，更安全
        User user = userService.findByUserId(userId);
        //delete password
        user.setPassword("");
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated(User.Update.class) User user) {
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, String> params) {
        //校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd)) {
            return Result.error("need full parameters");
        }
        //校验原密码是否正确
        Map<String, Object> claims = ThreadLocalUtils.get();
        int userId = (Integer) claims.get("id");
        User user = userService.findByUserId(userId);
        boolean match = BCryptUtils.matches(oldPwd, user.getPassword());
        if(match) {
            userService.updatePassword(newPwd);
            return Result.success();
        } else {
            return Result.error("old password is wrong");
        }
    }

    @DeleteMapping("/delete")
    public Result delete(Integer userId) {
        User user = userService.findByUserId(userId);
        if(user == null) {
            return Result.error("no this user");
        } else {
            userService.delete(userId);
            return Result.success();
        }
    }

}
