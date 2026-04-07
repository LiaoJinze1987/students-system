package com.liao.mapper;

import com.liao.pojo.Score;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper {

    @Insert("insert into score(student_id,term,content,remark,create_time,modify_time) " +
            "values(#{studentId},#{term},#{content},#{remark},now(),now())")
    void add(Score score);

    @Select("select * from score where student_id=#{studentId}")
    List<Score> list(Integer studentId);

    @Delete("delete from score where id=#{id}")
    void delete(Integer id);
}
