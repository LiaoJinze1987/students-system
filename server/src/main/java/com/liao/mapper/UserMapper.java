package com.liao.mapper;

import com.liao.pojo.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username} and clazz=#{clazz}")
    User findByNameAndClass(String username, String clazz);

    @Select("select * from user where id=#{id}")
    User findById(Integer id);

    @Select("select * from user where clazz=#{clazz} and is_master=1")
    User findMasterByClass(String clazz);

    @Select("select id,username,type,clazz,email from user where clazz=#{clazz} and type='student'")
    List<User> findClassStudents(String clazz);

    @Select("select id,username,type,clazz,email from user where type='student'")
    List<User> findAllStudents();

    @Select("select id,username,type,clazz,email from user where type='teacher'")
    List<User> findAllTeachers();

    @Insert("insert into user(username,password,type,clazz,email,create_time,modify_time) " +
            "values(#{username},#{password},#{type},#{clazz},#{email},now(),now())")
    void addUser(User user);

    @Update("update user set clazz=#{clazz},email=#{email},modify_time=now() where id=#{id}")
    void update(User user);

    @Update("update user set password=#{newPwd},modify_time=now() where id=#{id}")
    void updatePassword(int id, String newPwd);

    @Delete("delete from user where id = #{id}")
    void delete(Integer id);
}
