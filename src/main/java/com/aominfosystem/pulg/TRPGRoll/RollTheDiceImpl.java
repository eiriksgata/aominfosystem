package com.aominfosystem.pulg.TRPGRoll;

import com.aominfosystem.controller.ResultMessageHandle;
import com.aominfosystem.mapper.CocAttributeMapper;
import com.aominfosystem.pulg.TRPGRoll.model.RollResult;
import com.aominfosystem.utils.Calc;
import com.aominfosystem.utils.MyBatisUtil;
import com.aominfosystem.utils.RegularExpressionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.aominfosystem.config.CustomTextConfig.*;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.pulg.impl
 * @date:2019/9/16
 **/
public class RollTheDiceImpl extends ResultMessageHandle implements RollTheDice {

    @Override
    public String RollAttribute(String parameter, long fromqq, long fromGroup) {

        //判定参数个数
        String inputData[] = parameter.split(" ");
        int random = 1 + (int) Math.floor(Math.random() * 100);

        if (inputData.length > 1) {
            //大成功
            if (random == 1){
                return customResult(rollRaAttributeBigSuccess,String.valueOf(random),inputData[1],inputData[0]);
            }
            //大失败
            if (random == 100){
                return customResult(rollRaAttributeBigFail,String.valueOf(random),inputData[1],inputData[0]);
            }
            if (random <= Integer.valueOf(inputData[1])) {
                return customResult(rollRaAttributeSuccess,String.valueOf(random),inputData[1],inputData[0]);
            } else {
                return customResult(rollRaAttributeFail,String.valueOf(random),inputData[1],inputData[0]);
            }
        } else {

            SqlSession session = MyBatisUtil.getSession();
            CocAttributeMapper cocAttributeMapper = session.getMapper(CocAttributeMapper.class);
            String findAttribute = cocAttributeMapper.findAttributeByFromQQ(fromqq, fromGroup);
            String regex = parameter + "\\d[0-9]*";
            if (findAttribute==null){
                return customResult(rollRaAttributeError,"查询不到数据");
            }
            String regexFindString = RegularExpressionUtils.getMatcherString(regex, findAttribute);
            String attributeValue = regexFindString.substring(parameter.length());

            //大成功判定
            if (random==1){
                return customResult(rollRaAttributeBigSuccess,String.valueOf(random),attributeValue,inputData[0]);
            }
            //大失败判定
            if (random==100){
                return customResult(rollRaAttributeBigFail,String.valueOf(random),attributeValue,inputData[0]);
            }
            //极难
            if (random <= (int) Math.floor(Float.valueOf(attributeValue)/5)){
                return customResult(rollRaAttributeExSuccess,String.valueOf(random),attributeValue,inputData[0]);
            }
            //困难
            if (random <= (int) Math.floor(Float.valueOf(attributeValue)/2)){
                return customResult(rollRaAttributeDifficultySuccess,String.valueOf(random),attributeValue,inputData[0]);
            }
            //普通
            if (random <= Integer.valueOf(attributeValue)) {
                return customResult(rollRaAttributeSuccess,String.valueOf(random),attributeValue,inputData[0]);
            } else {
                return customResult(rollRaAttributeFail,String.valueOf(random),attributeValue,inputData[0]);
            }

        }

    }

    @Override
    public String RollCustomAttribute(String parameter, long fromqq, long fromGroup) {
        return null;
    }

    @Override
    public String RollRandom(String parameter, long fromqq, long fromGroup) {
        String[] result = formulaCalculation(parameter);
        return customResult(rollRSuccess,result[0],result[1]);
    }

    @Override
    public String RollSCCheck(String parameter, long fromqq, long fromGroup) {

        String parameterType[] = parameter.split(" ");
        String formula;
        int random = 1 + (int) Math.floor(Math.random() * 100);
        if (parameterType.length>1){
            int inputValue = Integer.valueOf(parameterType[1]);
            formula = parameterType[0];
            if (random<inputValue){

            }

        }




        return null;
    }

    @Override
    public String RollSHCheck(String parameter, long fromqq, long fromGroup) {
        return null;
    }


    public static String[] formulaCalculation(String formula) {
        String regex = "\\d[0-9]*d\\d[0-9]*";
        String result[] = new String[2];
        List<String> randomKeyList = RegularExpressionUtils.getMatchers(regex, formula);
        String[] value;

        for (int i = 0; i < randomKeyList.size(); i++) {
            value = randomKeyList.get(i).split("d");
            for (int j = 0; j < Integer.valueOf(value[0]); j++) {
                int count = 1 + (int) Math.floor(Math.random() * Integer.valueOf(value[1]));
                formula = formula.replace(randomKeyList.get(i), String.valueOf(count));
            }

        }
        result[0] = formula;
        result[1] = String.valueOf(new Calc(formula).getResult());

        return result;
    }


    private static RollResult numberValueCuntAddition(String[] addition) {
        RollResult rollResult = new RollResult();
        StringBuilder result = new StringBuilder();
        String value[];
        int resultCount = 0;
        if (addition.length > 1) {
            for (int i = 0; i < addition.length; i++) {
                value = addition[i].split("d");
                if (value.length > 1) {
                    int count = 0;
                    for (int j = 0; j < Integer.valueOf(value[0]); j++) {
                        count = 1 + (int) Math.floor(Math.random() * Integer.valueOf(value[1]));
                        resultCount += count;
                        result.append(count).append("+");
                    }
                } else {
                    result.append(value[0]).append("+");
                }
            }
            rollResult.setCount(resultCount);
            rollResult.setProcess(result.toString().substring(0, result.length() - 1) + "=");
        } else {
            rollResult.setProcess("");
            rollResult.setCount(Integer.valueOf(addition[0]));
        }

        return rollResult;
    }


}
