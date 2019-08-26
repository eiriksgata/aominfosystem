package com.aominfosystem.mapper;

import com.aominfosystem.pojo.Note;
import com.aominfosystem.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper{
    @Select("select * from user where fromqq=#{fromqq}")
    User findByFromqq(long fromqq);

}
