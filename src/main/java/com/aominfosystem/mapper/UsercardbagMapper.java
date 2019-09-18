package com.aominfosystem.mapper;

import com.aominfosystem.pojo.Usercardbag;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UsercardbagMapper {

    @Select("select * from usercardbag where cardGroupName = #{cardGroupName}")
    public List<Usercardbag> findCardInfoByCardGroupName(@Param("cardGroupName") String cardGroupName);

    @Select("select * from usercardbag where fromqq = #{fromqq}")
    public List<Usercardbag> findByFromqq(@Param("fromqq") long fromqq);

    @Select("select * from usercardbag where fromqq = #{fromqq} and id = #{id}")
    public Usercardbag findByFromqqAndId(@Param("id") long id ,@Param("fromqq") long fromqq);

    @Insert("insert into usercardbag (id,fromqq,cardGroupName,cardName,cardDescribe)" +
            "values (#{id},#{fromqq},#{cardGroupName},#{cardName},#{cardDescribe})")
    public int save(Usercardbag usercardbag);

    @Delete("delete from usercardbag where id = #{id}")
    public int delete(@Param("id") long id);


}
