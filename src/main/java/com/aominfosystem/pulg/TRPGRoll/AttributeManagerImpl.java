package com.aominfosystem.pulg.TRPGRoll;

import com.aominfosystem.controller.ResultMessageHandle;
import com.aominfosystem.mapper.CocAttributeMapper;
import com.aominfosystem.pojo.CocAttribute;
import com.aominfosystem.utils.MyBatisUtil;
import com.aominfosystem.utils.RegularExpressionUtils;
import com.sobte.cqp.jcq.entity.Member;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.aominfosystem.config.CustomTextConfig.*;
import static com.sobte.cqp.jcq.event.JcqApp.CQ;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.pulg.TRPGRoll
 * @date:2019/9/16
 **/
public class AttributeManagerImpl extends ResultMessageHandle implements AttributeManager {


    //.st
    @Override
    public String playerAttributeEntry(String parameter, long fromqq, long fromGroup) {

        if (parameter != null && !parameter.equals("") && parameter.length() > 0) {
            Member member = CQ.getGroupMemberInfo(fromGroup, fromqq, true);
            SqlSession session = MyBatisUtil.getSession();
            CocAttributeMapper cocAttributeMapper = session.getMapper(CocAttributeMapper.class);

            String selectAttribute = cocAttributeMapper.findAttributeByFromQQ(fromqq, fromGroup);
            if (selectAttribute == null) {
                CocAttribute addData = new CocAttribute();
                addData.setQq(fromqq);
                addData.setPlayer(member.getCard());
                addData.setAttribute(parameter);
                addData.setFromGroup(fromGroup);
                try {
                    cocAttributeMapper.save(addData);
                } catch (Exception e) {
                    System.out.println(e.toString());
                    return customResult(attributeEntryMessageError, e.toString());
                }
            } else {
                cocAttributeMapper.updateAttributeAndPalyerByQQ(parameter, fromqq, fromGroup,member.getCard());
            }
            MyBatisUtil.closeSession();
            try {
                String playerHpMax = RegularExpressionUtils.getMatcherString("体力\\d[0-9]*", parameter).substring(2);
                String playerHp = RegularExpressionUtils.getMatcherString("hp\\d[0-9]*", parameter).substring(2);
                String memberNike = member.getCard() + "[HP:" + playerHp + "/" + playerHpMax + "]";
                CQ.setGroupCard(fromGroup, fromqq, memberNike);
            } catch (Exception e) {
                return customResult(attributeEntryMessageSuccess,member, "没有找到hp或者体力属性，故不自动修改昵称");
            }


            return customResult(attributeEntryMessageSuccess, member, "");
        }

        return customResult(attributeEntryMessageError, "输入格式错误");
    }

    //.sta
    @Override
    public String playerAttributeSet(String parameter, long fromQQ, long fromGroup) {

        CocAttributeMapper cocAttributeMapper = MyBatisUtil.getSession().getMapper(CocAttributeMapper.class);
        String findResult = cocAttributeMapper.findAttributeByFromQQ(fromQQ, fromGroup);
        String data[] = parameter.split(" ");
        String regex = data[0] + "\\d[0-9]*";
        List<String> regexResult = RegularExpressionUtils.getMatchers(regex, findResult);
        String setStr = findResult.replace(regexResult.get(0), data[0] + data[1]);
        try {
            cocAttributeMapper.updateAttributeByFromQQ(setStr, fromQQ, fromGroup);
        } catch (Exception e) {
            System.out.println(e.toString());
            return customResult(attributeUpdateMessageError, "更新数据异常");
        }
        MyBatisUtil.closeSession();
        return customResult(attributeUpdateMessageSuccess,CQ.getGroupMemberInfo(fromGroup,fromQQ,true));
    }

    //.deleteAttribute
    @Override
    public String playerAttributeDelete(long fromQQ, long fromGroup) {
        CocAttributeMapper cocAttributeMapper = MyBatisUtil.getSession().getMapper(CocAttributeMapper.class);
        try {
            cocAttributeMapper.deleteByFromQQAndGroup(fromQQ, fromGroup);
        } catch (Exception e) {
            System.out.println(e.toString());
            return customResult(attributeDeleteMessageError, "服务器异常");
        }
        MyBatisUtil.closeSession();
        return customResult(attributeDeleteMessageSuccess);
    }


    @Override
    public String playerAttributeFindAll(long fromQQ, long fromGroup) {
        CocAttributeMapper cocAttributeMapper = MyBatisUtil.getSession().getMapper(CocAttributeMapper.class);
        String attribute = cocAttributeMapper.findAttributeByFromQQ(fromQQ, fromGroup);
        MyBatisUtil.closeSession();
        return attribute;
    }

    @Override
    public String playerAttributeFindByValue(String parameter, long fromQQ, long fromGroup) {
        CocAttributeMapper cocAttributeMapper = MyBatisUtil.getSession().getMapper(CocAttributeMapper.class);
        String regex = parameter + "\\d[0-9]*";
        List<String> result = RegularExpressionUtils.getMatchers(regex, cocAttributeMapper.findAttributeByFromQQ(fromQQ, fromGroup));
        return result.size() > 0 ? customResult(attributeFindByValueSuccess,CQ.getGroupMemberInfo(fromGroup,fromQQ), result.get(0)) : customResult(attributeFindByValueError);
    }

}
