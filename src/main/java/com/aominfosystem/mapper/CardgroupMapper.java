package com.aominfosystem.mapper;

import com.aominfosystem.pojo.Cardgroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface CardgroupMapper {

    @Select("select * from cardgroup where groupName = #{groupName}")
    public List<Cardgroup> findCardInfoByCardGroupName(String groupNmae);

    @Insert("insert into cardgroup (id,groupName,cardName,cardDescribe) " +
            "values (#{id},#{groupName},#{cardName},#{cardDescribe})")
    public int save(Cardgroup cardgroup);

    @Delete("delete from cardgroup where id = #{id}")
    public int delete(long id);

}
