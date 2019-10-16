package com.aominfosystem.controller;


import com.aominfosystem.config.GlobalConfig;
import com.aominfosystem.utils.MyBatisUtil;
import com.aominfosystem.mapper.GroupserviceslistMapper;
import com.aominfosystem.pojo.Groupserviceslist;
import org.apache.ibatis.session.SqlSession;
import org.meowy.cqp.jcq.entity.IMsg;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

public class MessageController implements IMsg {

    private Instructions instructions = new Instructions();

    public MessageController() {
    }

    public String messageAfferent(int msgId, long fromGroup, long fromqq, String fromAnonymous, String msg) {

        //启动本地配置
        if (GlobalConfig.usingLocalData){

            boolean inBlacklist = false;
            String QQtoString = String.valueOf(fromqq);
            String grouptoString = String.valueOf(fromGroup);
            for (int i=0;i<GlobalConfig.groupBlacklist.length;i++){
                if (grouptoString.equals(GlobalConfig.groupBlacklist[i])){
                    inBlacklist = true;
                    break;
                }
            }

            for (int i=0;i<GlobalConfig.userBlacklist.length;i++){
                if (QQtoString.equals(GlobalConfig.userBlacklist[i])){
                    inBlacklist = true;
                    break;
                }
            }

            if (msg != null && !inBlacklist) {
                //System.out.println(instructions.directiveJudgment(fromGroup, msg, fromqq));


                return instructions.directiveJudgment(fromGroup, msg, fromqq);
            }


        }
        if (!GlobalConfig.usingLocalData){
            boolean inOnlinBlacklist = false;


            SqlSession session = MyBatisUtil.getSession();
            GroupserviceslistMapper groupserviceslistMapper = session.getMapper(GroupserviceslistMapper.class);
            List<Groupserviceslist> list = groupserviceslistMapper.findAll();
            session.close();

            for (Groupserviceslist aList : list) {
                if (aList.getGroupnumber() == fromqq || aList.getGroupnumber() == fromGroup) {
                    inOnlinBlacklist = true;
                    break;
                }
            }

            if (msg!=null && !inOnlinBlacklist){
                return instructions.directiveJudgment(fromGroup, msg, fromqq);
            }
        }



        return null;


    }


}
