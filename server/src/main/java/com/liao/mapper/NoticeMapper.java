package com.liao.mapper;

import com.liao.pojo.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {

    @Insert("insert into notice(title,content,author,create_time,modify_time) " +
            "values(#{title},#{content},#{author},now(),now())")
    void add(Notice notice);

    @Select("select * from notice where id=#{id}")
    Notice findById(Integer id);

    @Select("select id,title,content from notice")
    List<Notice> list();

    @Delete("delete from notice where id=#{id}")
    void delete(Integer id);
}
