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

/*
        WebClient webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常
        webClient.getOptions().setActiveXNative(false);

        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);

        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setTimeout(Integer.MAX_VALUE);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        HtmlPage rootPage = webClient.getPage("https://music.163.com/#/search/#/?s=soldou&type=1002");
        webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束

        webClient.getOptions().setTimeout(20000);//设置“浏览器”的请求超时时间
        webClient.setJavaScriptTimeout(20000);//设置JS执行的超时时间

        FileUtils.fileLinesWrite("htmlpage\\getPage.txt",rootPage.asXml(),false);
        //System.out.println(rootPage.asXml());*/
        ObjectMapper mapper = new ObjectMapper();

        //MapType javaType = mapper.getTypeFactory().constructType(HashMap.class,String.class,);
        SearchBeanVo dataVo = new SearchBeanVo();

        HttpClientResult urlResult = new HttpClientResult();
        try {
            urlResult = HttpClientUtils.doGet("http://134.175.43.199/datageneration-Base/music/api/search/song/netease?key=taylor&limit=5&page=1");
            System.out.println(urlResult.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        dataVo = mapper.readValue(urlResult.getContent(),SearchBeanVo.class);
        System.out.println(dataVo.toString());
        for (int i = 0; i < dataVo.getSongList().size(); i++) {
            System.out.println(dataVo.getSongList().get(i).getId());
        }


    }


}
