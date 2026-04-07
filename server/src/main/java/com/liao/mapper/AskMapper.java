package com.liao.mapper;

import com.liao.pojo.Ask;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AskMapper {

    @Insert("insert into ask(type,content,student_id,student_name,master_id,student_master,create_time,modify_time) " +
            "values(#{type},#{content},#{studentId},#{studentName},#{masterId},#{studentMaster},now(),now())")
    void add(Ask ask);

    @Select("select id,type,student_id,student_name,master_id,content,status from ask where master_id=#{masterId}")
    List<Ask> list(Integer masterId);

    @Select("select * from ask where id=#{id}")
    Ask info(Integer id);

    @Delete("delete from ask where id = #{id}")
    void delete(Integer id);

    @Update("update ask set status=#{status} where id = #{id}")
    void update(Integer id, Integer status);
}
