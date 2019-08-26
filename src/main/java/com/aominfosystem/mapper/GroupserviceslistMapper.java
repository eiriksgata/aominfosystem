package com.aominfosystem.mapper;


import com.aominfosystem.pojo.Groupserviceslist;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GroupserviceslistMapper {

    @Select("select * from groupserviceslist")
    List<Groupserviceslist> findAll();

    @Insert("insert into groupserviceslist (id,groupnumber) " +
            "values (#{id},#{groupnumber}")
    int save(Groupserviceslist Groupserviceslist);

    @Update("update groupserviceslist set groupnumber=#{groupnumber} where = id=#{id}")
    int update(Groupserviceslist Groupserviceslist);

    @Delete("delete from groupserviceslist where id = #{id}")
    int delete(long id);

}
