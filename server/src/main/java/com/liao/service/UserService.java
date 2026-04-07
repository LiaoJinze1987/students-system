package com.liao.service;

import com.liao.pojo.User;

import java.util.List;

public interface UserService {
    //根据用户名和班级查询用户
    User findByNameAndClass(String username, String clazz);
    //根据用户 id 查询用户
    User findByUserId(Integer id);
    //根据 class 找到对应担任老师
    User findMasterByClass(String clazz);
    //根据 class 找到对应班级学生
    List<User> findClassStudents(String clazz);
    //全部学生
    List<User> findAllStudents();
    //全部老师
    List<User> findAllTeachers();
    //注册用户到数据库
    void register(User user);
    //更新用户信息
    void update(User user);
    //修改密码
    void updatePassword(String newPwd);
    //删除用户
    void delete(Integer id);
}
