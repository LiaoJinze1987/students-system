package com.liao.mapper;

import com.liao.pojo.Clazz;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {

    @Select("select * from class where name=#{className}")
    Clazz findByName(String className);

    @Select("select * from class where id=#{id}")
    Clazz findById(Integer id);

    @Select("select id,name from class")
    List<Clazz> list();

    @Insert("insert into class(name,header,remark,create_time,modify_time) " +
            "values(#{name},#{header},#{remark},now(),now())")
    void add(Clazz clazz);

    @Update("update class set name=#{name},header=#{header},remark=#{remark},modify_time=now() where id=#{id}")
    void update(Clazz clazz);

    @Delete("delete from class where id = #{id}")
    void delete(Integer id);
}
