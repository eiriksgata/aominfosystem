package com.aominfosystem.controller;


import com.aominfosystem.config.CustomTextConfig;
import com.aominfosystem.utils.RegularExpressionUtils;

import java.util.List;

import static com.aominfosystem.config.CustomTextConfig.customText;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.controller
 * @date:2019/9/17
 **/
public class ResultMessageHandle {


    protected String customResult(String type,String... value){

        String regex = "\\{.*?\\}";
        String resultText = customText.get(type);
        List<String> resultList = RegularExpressionUtils.getMatchers(regex,resultText);
        for (int i=0;i<resultList.size();i++){
            int inputId = Integer.valueOf(resultList.get(i).substring(1,resultList.get(i).length()-1));
            resultText = resultText.replace("{" + inputId + "}",value[inputId]);
        }
        return resultText;
    }





}
