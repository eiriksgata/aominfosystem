package com.aominfosystem.controller;


import com.aominfosystem.config.GlobalConfig;
import com.aominfosystem.dto.MyBatisUtil;
import com.aominfosystem.mapper.GroupserviceslistMapper;
import com.aominfosystem.pojo.Groupserviceslist;
import com.sobte.cqp.jcq.entity.IMsg;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class MessageController implements IMsg {

    Instructions instructions = new Instructions();

    public MessageController() {
    }

    public String messageAfferent(int msgId, long fromGroup, long fromqq, String fromAnonymous, String msg) {


        if (GlobalConfig.usingLocalData){
            Boolean inBlacklist = false;
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
            Boolean inOnlinBlacklist = false;


            SqlSession session = MyBatisUtil.getSqlSession();
            GroupserviceslistMapper groupserviceslistMapper = session.getMapper(GroupserviceslistMapper.class);
            List<Groupserviceslist> list = groupserviceslistMapper.findAll();
            session.close();

            for (int i=0;i<list.size();i++){
                if (list.get(i).getGroupnumber() == fromqq || list.get(i).getGroupnumber() == fromGroup){
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
