package com.aominfosystem.controller;


import com.aominfosystem.utils.RegularExpressionUtils;
import com.sobte.cqp.jcq.entity.Member;

import java.util.List;

import static com.aominfosystem.config.CustomTextConfig.customText;
import static com.sobte.cqp.jcq.event.JcqApp.CC;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.controller
 * @date:2019/9/17
 **/
public class ResultMessageHandle {


    protected String customResult(String type,String... value){

        String regex = "\\{[0-9]*?\\}";
        String resultText = customText.get(type);
        List<String> resultList = RegularExpressionUtils.getMatchers(regex,resultText);
        for (int i=0;i<resultList.size()&&i<value.length;i++){

            int inputId = Integer.valueOf(resultList.get(i).substring(1,resultList.get(i).length()-1));
            resultText = resultText.replace("{" + inputId + "}",value[inputId]);
        }
        return resultText;
    }

    protected String customResult(String type,Member member,String... value){

        String regex = "\\{.*?\\}";
        String processingResult = customResult(type,value);
        List<String> regexResultList = RegularExpressionUtils.getMatchers(regex,processingResult);
        for(int i=0;i<regexResultList.size();i++){
            processingResult = processingResult.replace(regexResultList.get(i),keyTypeProcessing(regexResultList.get(i),member));
        }

        return processingResult;
    }

    private String keyTypeProcessing(String input,Member member){

        switch (input){
            case "{member.getQQ}":
                return String.valueOf(member.getQqId());
            case "{member.getCard}":
                return member.getCard();
            case "{member.getNick}":
                return member.getNick();
            case "{member.getTitle":
                return member.getTitle();
            case "member.getArea":
                return member.getArea();
            case "member.getAge":
                return String.valueOf(member.getAge());
            case "member.getGender":
                return String.valueOf(member.getGender());
            case "{at}":
                return CC.at(member.getQqId());
            default:
                return input;
        }

    }



}
