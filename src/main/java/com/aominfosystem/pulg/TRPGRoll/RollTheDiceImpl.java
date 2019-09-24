package com.aominfosystem.pulg.TRPGRoll;

import com.aominfosystem.controller.ResultMessageHandle;
import com.aominfosystem.mapper.CocAttributeMapper;
import com.aominfosystem.pojo.CocAttribute;
import com.aominfosystem.utils.Calc;
import com.aominfosystem.utils.MyBatisUtil;
import com.aominfosystem.utils.RegularExpressionUtils;
import com.aominfosystem.utils.TypeTesting;
import com.sobte.cqp.jcq.entity.Group;
import com.sobte.cqp.jcq.entity.Member;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.aominfosystem.config.CustomTextConfig.*;
import static com.sobte.cqp.jcq.event.JcqApp.CQ;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.pulg.impl
 * @date:2019/9/16
 **/
public class RollTheDiceImpl extends ResultMessageHandle implements RollTheDice {

    //ra
    @Override
    public String rollAttribute(String parameter, long fromQQ, long fromGroup) {

        //判定参数个数
        String inputData[] = parameter.split(" ");
        Member member = CQ.getGroupMemberInfo(fromGroup, fromQQ, true);
        int random = 1 + (int) Math.floor(Math.random() * 100);

        if (inputData.length > 1) {
            //大成功
            if (random == 1) {
                return customResult(rollRaAttributeBigSuccess, member, String.valueOf(random), inputData[1], inputData[0]);
            }
            //大失败
            if (random == 100) {
                return customResult(rollRaAttributeBigFail, member, String.valueOf(random), inputData[1], inputData[0]);
            }

            //ex

            if (random <= (int) Math.floor(Float.valueOf(inputData[1]) / 5)) {
                return customResult(rollRaAttributeBigFail, member, String.valueOf(random), inputData[1], inputData[0]);
            }
            //dif
            if (random <= (int) Math.floor(Float.valueOf(inputData[1]) / 2)) {
                return customResult(rollRaAttributeBigFail, member, String.valueOf(random), inputData[1], inputData[0]);
            }


            if (random <= Integer.valueOf(inputData[1])) {
                return customResult(rollRaAttributeSuccess, member, String.valueOf(random), inputData[1], inputData[0]);
            } else {
                return customResult(rollRaAttributeFail, member, String.valueOf(random), inputData[1], inputData[0]);
            }
        } else {

            SqlSession session = MyBatisUtil.getSession();
            CocAttributeMapper cocAttributeMapper = session.getMapper(CocAttributeMapper.class);
            String findAttribute = cocAttributeMapper.findAttributeByFromQQ(fromQQ, fromGroup);
            String regex = parameter + "\\d[0-9]*";
            if (findAttribute == null) {
                return customResult(rollRaAttributeError, "查询不到数据");
            }
            String regexFindString = RegularExpressionUtils.getMatcherString(regex, findAttribute);
            String attributeValue = regexFindString.substring(parameter.length());

            //大成功判定
            if (random == 1) {
                return customResult(rollRaAttributeBigSuccess, member, String.valueOf(random), attributeValue, inputData[0]);
            }
            //大失败判定
            if (random == 100) {
                return customResult(rollRaAttributeBigFail, member, String.valueOf(random), attributeValue, inputData[0]);
            }
            //极难
            if (random <= (int) Math.floor(Float.valueOf(attributeValue) / 5)) {
                return customResult(rollRaAttributeExSuccess, member, String.valueOf(random), attributeValue, inputData[0]);
            }
            //困难
            if (random <= (int) Math.floor(Float.valueOf(attributeValue) / 2)) {
                return customResult(rollRaAttributeDifficultySuccess, member, String.valueOf(random), attributeValue, inputData[0]);
            }
            //普通
            if (random <= Integer.valueOf(attributeValue)) {
                return customResult(rollRaAttributeSuccess, member, String.valueOf(random), attributeValue, inputData[0]);
            } else {
                return customResult(rollRaAttributeFail, member, String.valueOf(random), attributeValue, inputData[0]);
            }

        }


    }

    @Override
    public String rollCustomAttribute(String parameter, long fromQQ, long fromGroup) {
        return null;
    }

    @Override
    public String rollRandom(String parameter, long fromQQ, long fromGroup) {

        try {
            String result[] = formulaCalculation(parameter, 0);
            System.out.println(result[0] + "=" +result [1]);
            return customResult(rollRandomSuccess,CQ.getGroupMemberInfo(fromGroup,fromQQ,true), result[0], result[1]);
        } catch (Exception e) {
            return customResult(rollRandomError, e.toString());
        }


    }

    @Override
    public String rollSCCheck(String parameter, long fromQQ, long fromGroup) {
        String parameterType[] = parameter.split(" ");
        int random = 1 + (int) Math.floor(Math.random() * 100);
        Member member = CQ.getGroupMemberInfo(fromGroup,fromQQ,true);

        if (parameterType.length > 1) {

            if (parameter.length() > 2) {
                return customResult(rollSCCheckError,member, "输入正确的指令");
            }

            int inputValue = Integer.valueOf(parameterType[1]);

            //0为成功数值 1为失败数值
            String data[] = parameterType[0].split("/");
            String failFormula = data[1];
            String successFormula = data[0];

            //大失败
            if (random == 100) {
                String regex = "\\d[0-9]*d\\d[0-9]*";
                List<String> randomKeyList = RegularExpressionUtils.getMatchers(regex, data[1]);
                String count;
                int surplus;
                String result[] = formulaCalculation(failFormula, 1);
                count = result[1];
                surplus = inputValue - Integer.valueOf(count);
                return customResult(rollSCCheckBigFail,member, String.valueOf(random), String.valueOf(inputValue), failFormula, count, String.valueOf(surplus));
            }

            //失败
            if (random > inputValue) {
                String result[] = formulaCalculation(failFormula, 0);
                int surplus = inputValue - Integer.valueOf(result[1]);
                return customResult(rollSCCheckFail,member, String.valueOf(random), String.valueOf(inputValue), result[0], result[1], String.valueOf(surplus));
            } else {
                //成功
                String result[] = formulaCalculation(successFormula, 0);
                int surplus = inputValue - Integer.valueOf(result[1]);
                return customResult(rollSCCheckSuccess,member, String.valueOf(random), String.valueOf(inputValue), result[0], result[1], String.valueOf(surplus));

            }

        } else {
            CocAttributeMapper cocAttributeMapper = MyBatisUtil.getSession().getMapper(CocAttributeMapper.class);
            String findAllAttribute = cocAttributeMapper.findAttributeByFromQQ(fromQQ, fromGroup);
            if (findAllAttribute == null) {
                return customResult(rollSCCheckError,member, "没有设置属性");
            }
            String regex = "san" + "\\d[0-9]*";
            String attribute = RegularExpressionUtils.getMatcherString(regex, findAllAttribute);
            int attributeValue = Integer.valueOf(attribute.substring(3));
            String result[];
            //0为成功数值 1为失败数值
            String data[] = parameterType[0].split("/");
            String failFormula = data[1];
            String successFormula = data[0];
            int surplus = 0;
            MyBatisUtil.closeSession();
            //大失败
            if (random == 100) {
                result = formulaCalculation(parameter, 1);
                surplus = attributeValue - Integer.valueOf(result[1]);
                String changeStr = "san " + surplus;
                new AttributeManagerImpl().playerAttributeSet(changeStr, fromQQ, fromGroup);
                return customResult(rollSCCheckBigFail,member, String.valueOf(random), String.valueOf(attributeValue), result[0], result[1], String.valueOf(surplus));
            }

            //失败
            if (random > attributeValue) {
                result = formulaCalculation(failFormula, 0);
                surplus = attributeValue - Integer.valueOf(result[1]);
                String changeStr = "san " + surplus;
                new AttributeManagerImpl().playerAttributeSet(changeStr, fromQQ, fromGroup);
                return customResult(rollSCCheckFail,member, String.valueOf(random), String.valueOf(attributeValue), result[0], result[1], String.valueOf(surplus));
            }

            //成功
            result = formulaCalculation(successFormula, 0);
            surplus = attributeValue - Integer.valueOf(result[1]);
            String changeStr = "san " + surplus;
            new AttributeManagerImpl().playerAttributeSet(changeStr, fromQQ, fromGroup);
            return customResult(rollSCCheckSuccess,member, String.valueOf(random), String.valueOf(attributeValue), result[0], result[1], String.valueOf(surplus));

        }
    }


    //.sh
    @Override
    public String rollSHCheck(String parameter, long fromQQ, long fromGroup) {

        //0为数值 1为属性  1个参数时 作为 hp属性
        String parameterTypeData[] = parameter.split(" ");
        CocAttributeMapper cocAttributeMapper;
        String findAllAttribute;
        String value;
        Member member = CQ.getGroupMemberInfo(fromGroup,fromQQ,true);
        //设置 改变数值的数据内容 如+5
        value = parameterTypeData[0];

        try {
            cocAttributeMapper = MyBatisUtil.getSession().getMapper(CocAttributeMapper.class);
        } catch (Exception e) {
            return customResult(rollSHCheckError, "数据库错误");
        }
        findAllAttribute = cocAttributeMapper.findAttributeByFromQQ(fromQQ, fromGroup);
        if (findAllAttribute == null) {
            return customResult(rollSHCheckError, "没有数据");
        }
        if (parameterTypeData[0].charAt(0) != '+' && parameterTypeData[0].charAt(0) != '-') {
            return customResult(rollSHCheckError, "格式错误");
        }

        if (parameterTypeData.length > 1 && parameterTypeData.length < 3) {

            String inputAttribute = parameterTypeData[1];
            String regex = inputAttribute + "\\d[0-9]*";
            String findAttribute = RegularExpressionUtils.getMatcherString(regex, findAllAttribute);
            String attributeData = findAttribute.substring(inputAttribute.length());

            //利用Calc来进行字符串计算
            String count = String.valueOf(new Calc(attributeData + value).getResult());

            findAllAttribute = findAllAttribute.replace(findAttribute, inputAttribute + count);
            cocAttributeMapper.updateAttributeByFromQQ(findAllAttribute, fromQQ, fromGroup);
            MyBatisUtil.closeSession();

            return customResult(rollSHCheckSuccess, member,inputAttribute, attributeData, value, count);
        } else {
            String findHPAttribute = RegularExpressionUtils.getMatcherString("hp\\d[0-9]*", findAllAttribute);
            String playerHpMax = RegularExpressionUtils.getMatcherString("体力\\d[0-9]*", findAllAttribute).substring(2);
            String playerNike = cocAttributeMapper.findPlayerNikeByFromQQAndGroup(fromQQ, fromGroup);
            String attributeData = findHPAttribute.substring(2);

            String count = String.valueOf(new Calc(attributeData + value).getResult());
            findAllAttribute = findAllAttribute.replace(findHPAttribute, "hp" + count);
            cocAttributeMapper.updateAttributeByFromQQ(findAllAttribute, fromQQ, fromGroup);
            CQ.setGroupCard(fromGroup, fromQQ, playerNike + "[HP:" + count + "/" + playerHpMax + "]");
            MyBatisUtil.closeSession();
            return customResult(rollSHCheckHPSuccess,member, attributeData, value, count);
        }

    }

    //.rb
    @Override
    public String rollReward(String parameter, long fromQQ, long fromGroup) {
        return rewardAndPunishmentMainFunction(parameter, fromGroup, fromQQ, 0);
    }


    //.rp
    @Override
    public String rollPunishment(String parameter, long fromQQ, long fromGroup) {
        return rewardAndPunishmentMainFunction(parameter, fromGroup, fromQQ, 1);
    }

    //.rh暗骰
    @Override
    public String rollHide(String parameter, long fromQQ, long fromGroup){
        Member member = CQ.getGroupMemberInfo(fromGroup,fromQQ,true);
        List<Group> group = CQ.getGroupList();
        String groupName = "";
        for (int i=0;i<group.size();i++){
            if (group.get(i).getId()==fromGroup){
                groupName = group.get(i).getName();
                break;
            }
        }
        String result = formulaCalculation("1d100",0)[1];
        CQ.sendPrivateMsg(fromQQ,customResult(rollRhPrivateSuccess,member,groupName,result));
        return customResult(rollRhSuccess,member);
    }


    //奖励骰和惩罚骰的公用函数 type 0 为奖励骰 1为惩罚
    private String rewardAndPunishmentMainFunction(String parameter, long fromGroup, long fromQQ, int type) {
        //0为骰子数 1为技能名 2数值
        String parameterTypeData[] = parameter.split(" ");
        int diceNumber;
        String attributeName;
        int random;
        int[] rewardValue;
        Member member = CQ.getGroupMemberInfo(fromGroup, fromQQ, true);
        //返回消息集合设置
        String messageType[] = new String[2];
        messageType[0] = "rollRb";
        messageType[1] = "rollRp";

        //现行判断参数数量
        if (parameterTypeData.length <= 2) {
            TypeTesting typeTesting = new TypeTesting();
            if (typeTesting.isInt(parameterTypeData[0])){
                diceNumber = Integer.valueOf(parameterTypeData[0]);
                attributeName = parameterTypeData[1];
                parameterTypeData = new String[1];
                parameterTypeData[0] = attributeName;

            }else {
                diceNumber = 1;
                attributeName = parameterTypeData[0];
            }


        } else {
            diceNumber = Integer.valueOf(parameterTypeData[0]);
            if (diceNumber > 10) diceNumber = 10;
            attributeName = parameterTypeData[1];

        }

        //生成1d100骰值
        random = 1 + (int) Math.floor(Math.random() * 100);
        //生成的奖励骰值
        rewardValue = rewardPunishmentFormulaCalculation(diceNumber);
        if (parameterTypeData.length < 4) {
            CocAttributeMapper cocAttributeMapper = MyBatisUtil.getSession().getMapper(CocAttributeMapper.class);
            String findAttribute = cocAttributeMapper.findAttributeByFromQQ(fromQQ, fromGroup);
            String attributeValue;
            if (findAttribute == null) {
                MyBatisUtil.closeSession();
                return customResult(messageType[type] + "Error", "输入数值错误");
            }

            String regex = attributeName + "\\d[0-9]*";
            String regexResult = RegularExpressionUtils.getMatcherString(regex, findAttribute);
            if (regexResult ==null){
                attributeValue = "";
            }else {
                attributeValue = regexResult.substring(attributeName.length());
            }



            StringBuilder resultReward = new StringBuilder();
            int resultValue = 0;
            //用于存放输入公式的返回结果 0过程 1结果
            String[] formulaResult;
            MyBatisUtil.closeSession();
            for (int i = 0; i < rewardValue.length; i++) {
                resultReward.append("[").append(rewardValue[i]).append("]");
            }

            if (parameterTypeData.length == 3 || parameterTypeData.length == 2) {
                try {
                    if (parameterTypeData[parameterTypeData.length - 1].charAt(0) == '+' || parameterTypeData[parameterTypeData.length - 1].charAt(0) == '-') {
                        attributeValue = attributeValue + parameterTypeData[parameterTypeData.length - 1];
                        formulaResult = formulaCalculation(attributeValue, 0);
                        attributeValue = formulaResult[1];
                    } else {
                        attributeValue = formulaCalculation(parameterTypeData[parameterTypeData.length - 1], 0)[1];
                    }


                } catch (Exception e) {
                    return customResult(messageType[type] + "Error", "输入数值错误");
                }
            }

            if (type == 0) {
                //判断奖励骰取舍
                if (rewardValue[0] * 10 < random) {
                    resultValue = random % 10 + rewardValue[0] * 10;
                } else {
                    resultValue = random;
                }
            }

            if (type == 1) {
                //判断惩罚骰取舍
                if (rewardValue[rewardValue.length - 1] * 10 > random) {
                    resultValue = random % 10 + rewardValue[rewardValue.length - 1] * 10;
                } else {
                    resultValue = random;
                }
            }

            //大成功
            if (resultValue == 1) {
                return customResult(messageType[type] + "BigSuccess", member, String.valueOf(random), attributeValue, attributeName, resultReward.toString(), String.valueOf(resultValue));
            }
            //大失败
            if (resultValue >= 100) {
                return customResult(messageType[type] + "BigFail", member, String.valueOf(random), attributeValue, attributeName, resultReward.toString(), String.valueOf(resultValue));
            }
            //极难
            if (resultValue <= (int) Math.floor(Float.valueOf(attributeValue) / 5)) {
                return customResult(messageType[type] + "ExSuccess", member, String.valueOf(random), attributeValue, attributeName, resultReward.toString(), String.valueOf(resultValue));

            }

            //困难
            if (resultValue <= (int) Math.floor(Float.valueOf(attributeValue) / 2)) {
                return customResult(messageType[type] + "DifficultySuccess", member, String.valueOf(random), attributeValue, attributeName, resultReward.toString(), String.valueOf(resultValue));
            }
            //普通
            if (resultValue < Integer.valueOf(attributeValue)) {
                return customResult(messageType[type] + "Success", member, String.valueOf(random), attributeValue, attributeName, resultReward.toString(), String.valueOf(resultValue));
            } else {
                return customResult(messageType[type] + "Fail", member, String.valueOf(random), attributeValue, attributeName, resultReward.toString(), String.valueOf(resultValue));
            }
        }

        return customResult(messageType[type] + "Error", "参数个数不正确");
    }


    //生成0-10 骰子数值
    private int[] rewardPunishmentFormulaCalculation(int diceNumber) {
        int randomList[] = new int[diceNumber];

        for (int i = 0; i < diceNumber; i++) {
            randomList[i] = (int) Math.floor(Math.random() * 11);
        }

        //排序
        for (int i = 0; i < randomList.length; i++) {
            for (int j = 0; j < randomList.length; j++) {
                if (randomList[i] < randomList[j]) {
                    int t = randomList[i];
                    randomList[i] = randomList[j];
                    randomList[j] = t;
                }
            }
        }

        return randomList;

    }


    //0 计算过程 1数值
    private String[] formulaCalculation(String formula, int type) {
        String regex = "\\d[0-9]*d\\d[0-9]*";
        String result[] = new String[2];
        List<String> randomKeyList = RegularExpressionUtils.getMatchers(regex, formula);
        String[] value;

        for (int i = 0; i < randomKeyList.size(); i++) {
            value = randomKeyList.get(i).split("d");
            // 0为默认公式计算 1 为随机数算满
            if (Integer.valueOf(value[0]) > 10) {
                value[0] = "10";
            }

            if (type == 0) {
                StringBuilder count = new StringBuilder("(");
                for (int j = 0; j < Integer.valueOf(value[0]); j++) {
                    int random = 1 + (int) Math.floor(Math.random() * Integer.valueOf(value[1]));
                    count.append(random).append("+");

                }
                count = new StringBuilder(count.substring(0, count.length() - 1) + ")");
                formula = formula.replace(randomKeyList.get(i), String.valueOf(count.toString()));
            } else {
                if (type == 1) {
                    StringBuilder newValue = new StringBuilder("(");
                    for (int j = 0; j < Integer.valueOf(value[0]); j++) {
                        newValue.append(randomKeyList.get(i).split("d")[1]).append("+");
                    }
                    newValue = new StringBuilder(newValue.substring(0, newValue.length() - 1) + ")");
                    formula = formula.replace(randomKeyList.get(i), newValue.toString());
                }
            }


        }
        result[0] = formula;
        result[1] = String.valueOf(new Calc(formula).getResult());

        return result;
    }


}
