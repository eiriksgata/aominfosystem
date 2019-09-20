package com.aominfosystem.pulg.TRPGRoll;

import com.aominfosystem.controller.ResultMessageHandle;
import com.aominfosystem.mapper.CocAttributeMapper;
import com.aominfosystem.utils.Calc;
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
 * @description: com.aominfosystem.pulg.impl
 * @date:2019/9/16
 **/
public class RollTheDiceImpl extends ResultMessageHandle implements RollTheDice {

    //ra
    @Override
    public String rollAttribute(String parameter, long fromqq, long fromGroup) {

        //判定参数个数
        String inputData[] = parameter.split(" ");
        Member member = CQ.getGroupMemberInfo(fromGroup, fromqq, true);
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
            if (random <= Integer.valueOf(inputData[1])) {
                return customResult(rollRaAttributeSuccess, member, String.valueOf(random), inputData[1], inputData[0]);
            } else {
                return customResult(rollRaAttributeFail, member, String.valueOf(random), inputData[1], inputData[0]);
            }
        } else {

            SqlSession session = MyBatisUtil.getSession();
            CocAttributeMapper cocAttributeMapper = session.getMapper(CocAttributeMapper.class);
            String findAttribute = cocAttributeMapper.findAttributeByFromQQ(fromqq, fromGroup);
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
    public String rollCustomAttribute(String parameter, long fromqq, long fromGroup) {
        return null;
    }

    @Override
    public String rollRandom(String parameter, long fromqq, long fromGroup) {
        try {
            String[] result = formulaCalculation(parameter, 0);
            return customResult(rollRandomSuccess, result[0], result[1]);
        } catch (Exception e) {
            return customResult(rollRandomError, e.toString());
        }


    }

    @Override
    public String rollSCCheck(String parameter, long fromqq, long fromGroup) {

        String parameterType[] = parameter.split(" ");
        int random = 1 + (int) Math.floor(Math.random() * 100);

        if (parameterType.length > 1) {

            if (parameter.length() > 2) {
                return customResult(rollSCCheckError, "输入正确的指令");
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
                return customResult(rollSCCheckBigFail, String.valueOf(random), String.valueOf(inputValue), failFormula, count, String.valueOf(surplus));
            }

            //失败
            if (random > inputValue) {
                String result[] = formulaCalculation(failFormula, 0);
                int surplus = inputValue - Integer.valueOf(result[1]);
                return customResult(rollSCCheckFail, String.valueOf(random), String.valueOf(inputValue), result[0], result[1], String.valueOf(surplus));
            } else {
                //成功
                String result[] = formulaCalculation(successFormula, 0);
                int surplus = inputValue - Integer.valueOf(result[1]);
                return customResult(rollSCCheckSuccess, String.valueOf(random), String.valueOf(inputValue), result[0], result[1], String.valueOf(surplus));

            }

        } else {
            CocAttributeMapper cocAttributeMapper = MyBatisUtil.getSession().getMapper(CocAttributeMapper.class);
            String findAllAttribute = cocAttributeMapper.findAttributeByFromQQ(fromqq, fromGroup);
            if (findAllAttribute == null) {
                return customResult(rollSCCheckError, "没有设置属性");
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
                new AttributeManagerImpl().playerAttributeSet(changeStr, fromqq, fromGroup);
                return customResult(rollSCCheckBigFail, String.valueOf(random), String.valueOf(attributeValue), result[0], result[1], String.valueOf(surplus));
            }

            //失败
            if (random > attributeValue) {
                result = formulaCalculation(failFormula, 0);
                surplus = attributeValue - Integer.valueOf(result[1]);
                String changeStr = "san " + surplus;
                new AttributeManagerImpl().playerAttributeSet(changeStr, fromqq, fromGroup);
                return customResult(rollSCCheckFail, String.valueOf(random), String.valueOf(attributeValue), result[0], result[1], String.valueOf(surplus));
            }

            //成功
            result = formulaCalculation(successFormula, 0);
            surplus = attributeValue - Integer.valueOf(result[1]);
            String changeStr = "san " + surplus;
            new AttributeManagerImpl().playerAttributeSet(changeStr, fromqq, fromGroup);
            return customResult(rollSCCheckSuccess, String.valueOf(random), String.valueOf(attributeValue), result[0], result[1], String.valueOf(surplus));

        }
    }


    //.sh
    @Override
    public String rollSHCheck(String parameter, long fromqq, long fromGroup) {

        //0为数值 1为属性  1个参数时 作为 hp属性
        String parameterTypeData[] = parameter.split(" ");
        CocAttributeMapper cocAttributeMapper;
        String findAllAttribute;
        String value;
        try {
            cocAttributeMapper = MyBatisUtil.getSession().getMapper(CocAttributeMapper.class);
        } catch (Exception e) {
            return customResult(rollSHCheckError, "数据库错误");
        }
        findAllAttribute = cocAttributeMapper.findAttributeByFromQQ(fromqq, fromGroup);
        if (findAllAttribute == null) {
            return customResult(rollSHCheckError, "没有数据");
        }

        if (parameterTypeData.length > 1 && parameterTypeData.length < 3) {

            String inputAttribute = parameterTypeData[1];
            String regex = inputAttribute + "\\d[0-9]*";
            String findAttribute = RegularExpressionUtils.getMatcherString(regex, findAllAttribute);
            String attributeData = findAttribute.substring(inputAttribute.length());

            //设置 改变数值的数据内容 如+5
            value = parameterTypeData[0];
            //利用Calc来进行字符串计算
            String count = String.valueOf(new Calc(attributeData + value).getResult());

            findAllAttribute = findAllAttribute.replace(findAttribute, inputAttribute + count);
            cocAttributeMapper.updateByFromQQ(findAllAttribute, fromqq, fromGroup);
            MyBatisUtil.closeSession();

            return customResult(rollSHCheckSuccess, inputAttribute, attributeData, value, count);
        } else {
            String findHPAttribute = RegularExpressionUtils.getMatcherString("hp\\d[0-9]*", findAllAttribute);
            String playerHpMax = RegularExpressionUtils.getMatcherString("体力\\d[0-9]*", findAllAttribute).substring(2);
            String playerNike = cocAttributeMapper.findPlayerNikeByFromQQAndGroup(fromqq, fromGroup);
            String attributeData = findHPAttribute.substring(2);
            value = parameterTypeData[0];
            String count = String.valueOf(new Calc(attributeData + value).getResult());
            findAllAttribute = findAllAttribute.replace(findHPAttribute, "hp" + count);
            cocAttributeMapper.updateByFromQQ(findAllAttribute, fromqq, fromGroup);
            CQ.setGroupCard(fromGroup, fromqq, playerNike + "[HP:" + count + "/" + playerHpMax + "]");
            MyBatisUtil.closeSession();
            return customResult(rollSHCheckHPSuccess, attributeData, value, count);
        }

    }


    //0 计算过程 1数值
    private static String[] formulaCalculation(String formula, int type) {
        String regex = "\\d[0-9]*d\\d[0-9]*";
        String result[] = new String[2];
        List<String> randomKeyList = RegularExpressionUtils.getMatchers(regex, formula);
        String[] value;

        for (int i = 0; i < randomKeyList.size(); i++) {
            value = randomKeyList.get(i).split("d");
            // 0为默认公式计算 1 为随机数算满
            if (type == 0) {
                StringBuilder count = new StringBuilder("(");
                for (int j = 0; j < Integer.valueOf(value[0]); j++) {
                    int random = 1 + (int) Math.floor(Math.random() * Integer.valueOf(value[1]));
                    System.out.println(random);
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
