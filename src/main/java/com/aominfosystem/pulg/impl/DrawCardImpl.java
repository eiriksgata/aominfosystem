package com.aominfosystem.pulg.impl;

import com.aominfosystem.config.GlobalConfig;
import com.aominfosystem.mapper.CardgroupMapper;
import com.aominfosystem.mapper.UsercardbagMapper;
import com.aominfosystem.pojo.Cardgroup;
import com.aominfosystem.pojo.Usercardbag;
import com.aominfosystem.pulg.DrawCard;
import com.aominfosystem.utils.MyBatisUtil;
import com.sobte.cqp.jcq.entity.Member;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.sobte.cqp.jcq.event.JcqApp.CC;
import static com.sobte.cqp.jcq.event.JcqApp.CQ;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.pulg.impl
 * @date:2019/9/5
 **/
public class DrawCardImpl implements DrawCard {

    @Override
    public String drawCardStart(String parameter, long fromqq) {
        StringBuilder result = new StringBuilder();
        SqlSession sqlSession = MyBatisUtil.getSession();
        CardgroupMapper cardgroupMapper = sqlSession.getMapper(CardgroupMapper.class);
        List<Cardgroup> cardGroup;
        Usercardbag userCarBag = new Usercardbag();
        UsercardbagMapper usercardbagMapper;
        int random;
        try {
            cardGroup = cardgroupMapper.findCardInfoByCardGroupName(parameter);
            if (cardGroup.size() == 0) {
                return "抱歉,该卡池中没有任何卡";
            }
        } catch (Exception e) {
            result.append("查询卡池类型错误，请查看控制台");
            System.out.println(e.toString());
            return result.toString();
        }
        random = (int) Math.floor(Math.random() * cardGroup.size());
        userCarBag.setFromqq(fromqq);
        userCarBag.setCardName(cardGroup.get(random).getCardName());
        userCarBag.setCardGroupName(cardGroup.get(random).getGroupName());
        userCarBag.setCardDescribe(cardGroup.get(random).getCardDescribe());
        usercardbagMapper = sqlSession.getMapper(UsercardbagMapper.class);
        try {
            usercardbagMapper.save(userCarBag);
        } catch (Exception e) {
            result.append("存取卡时出现了错误,查看控制台");
            System.out.println("存取卡时出现了错误 :" + e.toString());
            return result.toString();
        }

        try {
            cardgroupMapper.delete(cardGroup.get(random).getId());

        } catch (Exception e) {
            result.append("删除卡池的卡时出现了错误,查看控制台");
            System.out.println("存取卡时出现了错误 :" + e.toString());
        }
        result.append(CC.at(fromqq)).append("\n")
                .append("卡牌名称:").append(cardGroup.get(random).getCardName()).append("\n")
                .append("卡牌所属:").append(cardGroup.get(random).getGroupName()).append("\n")
                //.append("卡牌描述:").append(cardGroup.get(random).getCardDescribe()).append("\n")
                .append("卡牌已放置你的卡包，使用>_findMyCard查看详细信息");
        sqlSession.commit();
        MyBatisUtil.closeSession();
        return result.toString();
    }

    @Override
    public String findMyCardBag(String parameter, long fromqq) {
        StringBuilder result = new StringBuilder();
        SqlSession sqlSession = MyBatisUtil.getSession();
        UsercardbagMapper usercardbagMapper = sqlSession.getMapper(UsercardbagMapper.class);
        List<Usercardbag> myCarBagList = usercardbagMapper.findByFromqq(fromqq);
        if (myCarBagList.size() > 0) {

            result.append(CC.at(fromqq)).append("\n").append("卡牌ID|卡牌名称|卡牌所属\n");
            for (int i = 0; i < myCarBagList.size(); i++) {
                result.append("[").append(myCarBagList.get(i).getId()).append("] ")
                        .append(myCarBagList.get(i).getCardName()).append(" | ")
                        .append(myCarBagList.get(i).getCardGroupName())
                        .append("\n");
            }
            result.append("使用>_seeCard X查看卡牌描述X为你的卡牌ID号");
        } else {
            result.append("你的卡包什么都没有");
        }
        MyBatisUtil.closeSession();

        return result.toString();
    }

    @Override
    public String useCard(String parameter, long fromqq) {
        SqlSession sqlSession = MyBatisUtil.getSession();
        UsercardbagMapper usercardbagMapper = sqlSession.getMapper(UsercardbagMapper.class);
        long findUseCard = 0;
        Usercardbag useCard;

        if (parameter.length()==0){
            return "输入的查询ID错误";
        }
        try{
            findUseCard = Long.valueOf(parameter);
        }catch (Exception e){
            return "错误的参数类型";
        }
        try{
            useCard = usercardbagMapper.findByFromqqAndId(findUseCard,fromqq);
        }catch (Exception e){
            System.out.println(e.toString());
            return "数据查询出错,请查看控制台";
        }
        if (useCard != null && useCard.getCardName()!= null){
            try{
                usercardbagMapper.delete(findUseCard);
                sqlSession.commit();
            }catch (Exception e){
                System.out.println(e.toString());

            }
        }
        MyBatisUtil.closeSession();
        assert useCard != null;
        return CC.at(fromqq)+"你使用了:" + useCard.getCardName();
    }


    @Override
    public String addCard(String parameter, long fromqq) {
        String result = null;
        // 0 为 添加的卡池名称 1为卡名 2卡的描述
        String inputValue[] = parameter.split(",");

        if (GlobalConfig.adminNumberList == null || GlobalConfig.adminNumberList.length == 0 || GlobalConfig.adminNumberList[0].equals("")) {
            result = addFunction(inputValue);


        } else {
            for (int i = 0; i < GlobalConfig.adminNumberList.length; i++) {
                if (String.valueOf(fromqq).equals(GlobalConfig.adminNumberList[i])) {
                    result = addFunction(inputValue);
                }
            }

        }

        return result;
    }

    @Override
    public String seeCard(String parameter, long fromqq) {
        StringBuilder result = new StringBuilder();
        SqlSession sqlSession = MyBatisUtil.getSession();
        long findCardId = 0;
        UsercardbagMapper usercardbagMapper = sqlSession.getMapper(UsercardbagMapper.class);
        Usercardbag findcard;
        if (parameter.length()==0){
            return "输入的查询ID错误";
        }
        try{
            findCardId = Long.valueOf(parameter);
        }catch (Exception e){
            return "错误的参数类型";
        }
        try{
            findcard = usercardbagMapper.findByFromqqAndId(findCardId,fromqq);
        }catch (Exception e){
            System.out.println(e.toString());
            return "数据查询出错,请查看控制台";
        }

        result.append("卡牌ID:").append("[").append(findcard.getId()).append("]").append("\n")
                .append("卡牌名称:").append(findcard.getCardName()).append("\n")
                .append("卡牌所属:").append(findcard.getCardGroupName()).append("\n")
                .append("持有人QQ和名称:").append(findcard.getFromqq()).append("").append("\n")
                .append("卡牌描述:").append(findcard.getCardDescribe()).append("\n");
        CQ.sendPrivateMsg(fromqq,result.toString());
        MyBatisUtil.closeSession();
        return "已私送查询信息，请查看";
    }

    private String manyDrawCard(int number) {

        return null;
    }

    private String addFunction(String[] inputValue) {
        String result = null;
        Cardgroup cardgroup = new Cardgroup();
        SqlSession sqlSession = MyBatisUtil.getSession();
        CardgroupMapper cardgroupMapper = sqlSession.getMapper(CardgroupMapper.class);
        if (inputValue[0] != null && inputValue[1] != null && inputValue[2] != null) {

            if (!inputValue[0].equals("") && !inputValue[1].equals("") && !inputValue[2].equals("")) {
                cardgroup.setGroupName(inputValue[0]);
                cardgroup.setCardName(inputValue[1]);
                cardgroup.setCardDescribe(inputValue[2]);
                result = "往" + inputValue[0] + "添加了一张卡.";
                try {
                    cardgroupMapper.save(cardgroup);
                    sqlSession.commit();
                } catch (Exception e) {
                    System.out.println(e.toString());
                    result = "添加异常，请检查控制台消息";
                }
            }
        }
        MyBatisUtil.closeSession();
        return result;

    }

}
