package com.aominfosystem.mapper;

import com.aominfosystem.pojo.Cardgroup;
import com.aominfosystem.pojo.CocAttribute;
import org.apache.ibatis.annotations.*;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.mapper
 * @date:2019/9/17
 **/
@Mapper
public interface CocAttributeMapper {

    @Insert("insert into coc_attribute (id,qq,attribute,player,from_group)values(#{id},#{qq},#{attribute},#{player},#{fromGroup})")
    public int save(CocAttribute cocAttribute);

    @Update("update coc_attribute set attribute = #{attribute},player=#{player} where qq = #{fromQQ} && from_group =#{fromGroup}")
    public void updateByFromQQ(@Param("attribute") String attribute, @Param("player") String player, @Param("fromQQ") long fromQQ,@Param("fromGroup") long fromGroup);

    @Select("select attribute from coc_attribute where qq = #{fromQQ} && from_group=#{fromGroup}")
    public String findAttributeByFromQQ(@Param("fromQQ") long fromQQ,@Param("fromGroup")long fromGroup);

    @Delete("delete from coc_attribute where qq=#{fromQQ} && from_group = #{fromGroup}")
    public int deleteByFromQQAndGroup(@Param("fromQQ") long fromQQ,@Param("fromGroup") long fromGroup);


}
