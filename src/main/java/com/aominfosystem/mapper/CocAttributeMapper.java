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
    int save(CocAttribute cocAttribute);

    @Update("update coc_attribute set attribute = #{attribute} where qq = #{fromQQ} && from_group =#{fromGroup}")
    void updateAttributeByFromQQ(@Param("attribute") String attribute, @Param("fromQQ") long fromQQ, @Param("fromGroup") long fromGroup);

    @Update("update coc_attribute set player = #{player}, attribute = #{attribute} where qq = #{fromQQ} && from_group =#{fromGroup}")
    void updateAttributeAndPalyerByQQ(@Param("attribute") String attribute, @Param("fromQQ") long fromQQ, @Param("fromGroup") long fromGroup, @Param("player") String player);

    @Select("select attribute from coc_attribute where qq = #{fromQQ} && from_group=#{fromGroup}")
    String findAttributeByFromQQ(@Param("fromQQ") long fromQQ, @Param("fromGroup") long fromGroup);

    @Delete("delete from coc_attribute where qq=#{fromQQ} && from_group = #{fromGroup}")
    int deleteByFromQQAndGroup(@Param("fromQQ") long fromQQ, @Param("fromGroup") long fromGroup);

    @Select("select player from coc_attribute where qq = #{fromQQ} && from_group=#{fromGroup}")
    String findPlayerNikeByFromQQAndGroup(@Param("fromQQ") long fromQQ, @Param("fromGroup") long fromGroup);
}
