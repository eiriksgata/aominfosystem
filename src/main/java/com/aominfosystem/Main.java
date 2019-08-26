package com.aominfosystem;

import com.aominfosystem.dto.MyBatisUtil;
import com.aominfosystem.mapper.GrouprecordMapper;
import com.aominfosystem.mapper.NoteMapper;
import com.aominfosystem.pojo.Grouprecord;
import com.aominfosystem.pojo.Note;
import com.aominfosystem.utils.OpenBrowserUtils;
import com.aominfosystem.utils.TypeTesting;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.aspectj.weaver.ast.Not;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.aominfosystem.utils.DownLoadUtils.downLoadFromUrl;

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
