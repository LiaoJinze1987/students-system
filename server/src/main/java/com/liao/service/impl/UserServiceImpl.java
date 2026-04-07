package com.liao.service.impl;

import com.liao.mapper.UserMapper;
import com.liao.pojo.User;
import com.liao.service.UserService;
import com.liao.utils.BCryptUtils;
import com.liao.utils.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByNameAndClass(String username, String clazz) {
        return userMapper.findByNameAndClass(username, clazz);
    }

    @Override
    public User findByUserId(Integer id) {
        return userMapper.findById(id);
    }

    @Override
    public User findMasterByClass(String clazz) {
        return userMapper.findMasterByClass(clazz);
    }

    @Override
    public List<User> findClassStudents(String clazz) {
        return userMapper.findClassStudents(clazz);
    }

    @Override
    public List<User> findAllStudents() {
        return userMapper.findAllStudents();
    }

    @Override
    public List<User> findAllTeachers() {
        return userMapper.findAllTeachers();
    }


    @Override
    public void register(User user) {
        //加密密码
        String encodedPassword = BCryptUtils.encode(user.getPassword());
        user.setPassword(encodedPassword);
        //添加到数据库
        userMapper.addUser(user);
    }

    @Override
    public void update(User user) {
        //添加到数据库
        userMapper.update(user);
    }

    @Override
    public void updatePassword(String newPwd) {
        String encodedPassword = BCryptUtils.encode(newPwd);
        Map<String, Object> claims = ThreadLocalUtils.get();
        Integer id = (Integer) claims.get("id");
        userMapper.updatePassword(id, encodedPassword);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }
}
