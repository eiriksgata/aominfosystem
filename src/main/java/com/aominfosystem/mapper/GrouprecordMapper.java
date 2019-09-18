package com.aominfosystem.mapper;


import com.aominfosystem.pojo.Grouprecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface GrouprecordMapper {

    @Insert("insert into grouprecord (id,msgid,fromgroup,fromqq,fromanonymous,msg,time) " +
            "values (#{id},#{msgid},#{fromgroup},#{fromqq},#{fromanonymous},#{msg},#{time})")
    int save(Grouprecord grouprecord);

    @Select("select * from grouprecord ")
    List<Grouprecord> findAll();
}
