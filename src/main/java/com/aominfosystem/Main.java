package com.aominfosystem;

import java.io.*;

/**
 * @author: create by admin
 * @version: v1.0
 * @description: com.aominfosystem
 * @date:2019/8/2
 **/
public class Main {



    public static void main(String args[]) throws IOException {

        /*SqlSession sqlSession = MyBatisUtil.getSqlSession();
        GrouprecordMapper grouprecordMapper = sqlSession.getMapper(GrouprecordMapper.class);
*//*
        Grouprecord grouprecord = new Grouprecord();
        grouprecord.setFromanonymous("11111");
        grouprecord.setFromgroup(11);
        grouprecord.setFromqq(111);
        grouprecord.setMsg("中文输入");
        grouprecord.setMsgid(11111);
        try{
            grouprecordMapper.save(grouprecord);
        }catch (Exception e){
            System.out.println(e);
        }
        sqlSession.commit();
        *//*
        List<Grouprecord> grouprecord =grouprecordMapper.findAll();
        for (int i=0;i<grouprecord.size();i++){
            System.out.println(grouprecord.get(i).getMsg());
        }

        sqlSession.close();*/
        /*OpenBrowserUtils openBrowserUtils = new OpenBrowserUtils();
        try {
            openBrowserUtils.browse2("http://localhost:8091");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //String usrstr ="https://keithtools-1257541903.cos.ap-guangzhou.myqcloud.com/test.zip";

        //downLoadFromUrl(usrstr,"test.zip","upload");


       // System.out.println();
       // Runtime.getRuntime().exec("cmd /c start explorer "+ System.getProperty("user.dir"));
       // System.out.println(random);


    }



}
