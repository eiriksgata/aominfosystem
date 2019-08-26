package com.aominfosystem.controller;


import com.aominfosystem.config.CreateSystemFile;
import com.aominfosystem.utils.MyBatisUtil;
import com.aominfosystem.mapper.GrouprecordMapper;
import com.aominfosystem.pojo.Grouprecord;
import com.sobte.cqp.jcq.entity.Member;
import org.apache.ibatis.session.SqlSession;
import com.aominfosystem.controller.cofig.GroupMessageCofig;

import java.sql.Timestamp;

import static com.aominfosystem.config.GlobalConfig.recordSwitch;
import static com.aominfosystem.config.GlobalConfig.usingLocalData;
import static com.aominfosystem.controller.Instructions.recordOpen;
import static com.aominfosystem.utils.FileUtils.fileLinesWrite;
import static com.sobte.cqp.jcq.event.JcqApp.CQ;

public class GroupMessageRecord {



    public GroupMessageRecord() {
    }

    public void messageRecord(int msgId, long fromGroup, long fromQQ, String fromAnonymous, String msg) {


        if (!usingLocalData) {

            Boolean saveOrNot = true;

            String nike;
            Member member = CQ.getGroupMemberInfo(fromGroup, fromQQ,true);
            if(member.getCard().equals("")){
                nike = member.getNick();
            }else if(member.getNick().equals("")){
                nike = String.valueOf(member.getQqId());
            }else {
                nike = member.getCard();
            }

            for (int j = 0; j < GroupMessageCofig.unMonitoringQQ.length; j++) {
                if (fromQQ == GroupMessageCofig.unMonitoringQQ[j]) {
                    saveOrNot = false;
                    System.out.println("该信息不在记录人员范围");
                    break;
                }
            }

            //防御措施
            if (fromQQ == 209135855) {
                saveOrNot = false;
            }

            if (saveOrNot && msg.length() < 500) {

                SqlSession sqlSession = MyBatisUtil.getSession();
                GrouprecordMapper grouprecordMapper = sqlSession.getMapper(GrouprecordMapper.class);

                for (int i = 0; i < GroupMessageCofig.monitoringGroupNumber.length; i++) {
                    Grouprecord grouprecord = new Grouprecord();
                    grouprecord.setFromanonymous(fromAnonymous);
                    grouprecord.setFromqq(fromQQ);
                    grouprecord.setFromgroup(fromGroup);
                    grouprecord.setMsgid(msgId);
                    grouprecord.setMsg( "[" + nike+ "]" + msg);
                    grouprecord.setTime(new Timestamp(System.currentTimeMillis()));
                    if (grouprecord.getFromgroup() == GroupMessageCofig.monitoringGroupNumber[i]) {
                        if (grouprecord.getMsg().length() >= GroupMessageCofig.messageMinLength) {
                            try {
                                grouprecordMapper.save(grouprecord);
                                sqlSession.commit();
                            } catch (Exception e) {
                                System.out.println("数据存储数据库中出现了问题" + e);
                            }

                        }
                    }

                }
            }
        }else {
            if (recordSwitch&&recordOpen){
                String likeText;
                Member member = CQ.getGroupMemberInfo(fromGroup, fromQQ,true);
                String name;
                if(member.getCard().equals("")){
                    name = member.getNick();
                }else if(member.getNick().equals("")){
                    name = String.valueOf(member.getQqId());
                }else {
                    name = member.getCard();
                }

                likeText = "[" + name + "]" + msg ;
                fileLinesWrite(CreateSystemFile.folderName + "\\"+ "localMessageRecord" + "\\" + fromGroup +".txt",likeText,true);
            }
        }




    }

    public void sendGroupMessage(String sendMessage) {

    }

}
