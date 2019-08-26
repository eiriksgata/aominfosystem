package com.aominfosystem.mapper;

import com.aominfosystem.pojo.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoteMapper {
    @Insert("insert into note (id,creator,grade,title,hide,text) " +
            "values (#{id},#{creator},#{grade},#{title},#{hide},#{text})")
    int save(Note note);

    @Update("update note set grade=#{grade},title=#{title},hide=#{hide},text=#{text}")
    int update(Note note);

    @Delete("delete from note where id = #{id}")
    int delete(long id);

    @Select("select * from note")
    List<Note> findAll();

    @Select("select * from note where id = #{id}")
    Note findByid(long id);

}
