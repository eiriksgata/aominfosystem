package com.aominfosystem;


import com.aominfosystem.json.searchbean.SearchBeanVo;
import com.aominfosystem.utils.HttpClientUtils;
import com.aominfosystem.vo.HttpClientResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: create by admin
 * @version: v1.0
 * @description: com.aominfosystem
 * @date:2019/8/2
 **/
public class Main {


    public static void main(String args[]) throws IOException {


        ObjectMapper mapper = new ObjectMapper();

        //MapType javaType = mapper.getTypeFactory().constructType(HashMap.class,String.class,);
        SearchBeanVo dataVo = new SearchBeanVo();

        HttpClientResult urlResult = new HttpClientResult();
        try {
            urlResult = HttpClientUtils.doGet("https://music-api-jwzcyzizya.now.sh/api/get/song/qq?id=003OUlho2HcRHC");
            System.out.println(urlResult.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        dataVo = mapper.readValue(urlResult.getContent(),SearchBeanVo.class);
        System.out.println(dataVo.toString());
        for (int i = 0; i < dataVo.getSongList().size(); i++) {
            System.out.println(dataVo.getSongList().get(i).getId());
        }
*/

    }


}
