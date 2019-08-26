package com.aominfosystem.controller;


import com.aominfosystem.config.CreateSystemFile;
import com.aominfosystem.controller.cofig.InstructionsConfig;
import com.aominfosystem.pulg.DrawUtils;
import com.aominfosystem.pulg.NotePulg;
import com.aominfosystem.pulg.impl.NotePulgImpl;
import com.aominfosystem.utils.ConfigurationFile;
import com.aominfosystem.utils.RegularExpressionUtils;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static com.sobte.cqp.jcq.event.JcqApp.CC;

public class Instructions {


    private NotePulgImpl notePulg = new NotePulgImpl();

    private boolean drawCooling = true;
    public static boolean recordOpen = false;

    public Instructions() {

    }

    /**
     * 指令类型判断
     *
     * @param msg
     * @param fromqq
     * @return
     */
    public String directiveJudgment(long fromGroup, String msg, long fromqq) {
        if (orderMessageConfirm(msg)) {
            //正则表达式
            String regex = ".*? ";
            Boolean flag = false;
            String type;
            String matchersStr;
            if (RegularExpressionUtils.getMatchers(regex, msg).size() == 0) {
                matchersStr = msg;
                type = matchersStr.substring(2);

            } else {
                matchersStr = RegularExpressionUtils.getMatchers(regex, msg).get(0);
                type = matchersStr.substring(2, matchersStr.length() - 1);
                //System.out.println(matchersStr);
            }


            //取剩下的参数值
            String parameter = msg.substring(matchersStr.length());
            switch (type) {
                case "help":
                    return returnHelpMessage();
                case "link":
                    return returnLinkMessage();
                case "note":
                    return notePulg.noteControl(parameter, fromqq);
                case "notehelp":
                    return notePulg.returnNotehelpMessage();
                case "notefind":
                    return notePulg.findNoteList(parameter, fromqq);
                case "noteopen":
                    return notePulg.openNote(parameter, fromqq);
                case "notedelete":
                    return notePulg.deleteNote(parameter, fromqq);
                case "draw":
                    return draw(fromGroup, fromqq);
                case "record":
                    return recordManegr();
                case "recordhelp":
                    return returnRecordHelpInfo();
                default:
                    return "不太清楚您输的什么指令呢";
            }
        } else {
            return null;
        }
    }

    public String returnRecordHelpInfo(){
        String result = "输入>_record后系统将会在后台激活消息记录状态，将会记录接收到的群消息。如果localMessageRecord目录没有那么系统将会创建。" +
                "开启消息记录后，所说的每一句话都会被追加记录在localMessageRecord文件夹中，如果需要单独取出消息内容，或者定期备份，请将该文件重命名即可。" +
                "如果需要关闭请再次输入>_record即可停止消息记录。" +
                "记录按照群号来进行划分。";

        return result;
    }
    public String recordManegr() {
        if (!recordOpen) {
            recordOpen = true;
        } else {
            recordOpen = false;
        }

        return null;
    }

    public String draw(long fromGourp, long fromqq) {

        String filePath = CreateSystemFile.folderName + "\\" + CreateSystemFile.configFileName;
        try {
            String readcool = ConfigurationFile.readCfgValue(filePath, "Draw", "cool", "true");

            Boolean cool = Boolean.valueOf(readcool);
            if (cool) {

            } else {
                Timer timer = new Timer();
                long time = 2000;
                String resultDraw = CC.at(fromqq) + DrawUtils.drawStart();

                try {
                    String result = ConfigurationFile.readCfgValue(filePath, "Draw", "drawCooling", "2000");
                    time = Long.valueOf(result);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                timer.schedule(new TimerTask() {
                    public void run() {
                        try {
                            ConfigurationFile.writeCfgValue("aominfosystemConf" + "\\" + "aominfosystemConfig.ini", "Draw", "cool", "false");
                            System.out.println("set false");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        timer.cancel();
                    }
                }, time);
                ConfigurationFile.writeCfgValue(filePath, "Draw", "cool", "true");

                // CQ.sendGroupMsg(fromGourp,);
                return resultDraw;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        return null;

    }

    /**
     * 判断消息是否具有指令性质
     *
     * @param msg
     * @return
     */
    public Boolean orderMessageConfirm(String msg) {

        if (msg.length() > 4) {
            if (msg.substring(0, 2).equals(InstructionsConfig.prefix)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * 介绍help指令的使用
     *
     * @return
     */
    private String returnHelpMessage() {
        String result;
        result = "欢迎使用help指令,该指令用于显示目前实装的所有指令类型" +
                "\n指令使用格式为：>_help 触发指令前提为>_" +
                "\n>_help:帮助指令" +
                "\n>_link:链接指令，返回一些链接" +
                "\n*>_notehelp:笔记帮助功能，里面介绍了具体的各个功能使用方法" +
                "\n>_draw:抽奖指令" +
                "\n>_rocordhelp:显示消息记录帮助信息" +
                "\n(带有*的功能需要开启远程连接才能使用)";
        return result;
    }

    /**
     * 返回link指令内容
     *
     * @return
     */
    public String returnLinkMessage() {
        String result;
        result = "1.插件后台系统进入操作界面:localhost:5424\n" +
                "2.作者博客网址:http://134.175.43.199/\n" +
                "4.web工具（目前有凯撒密码自动分析工具、任意进制转换）:https://keith404.gitee.io/tool/\n" +
                "5.测试数据生成工具（生成身份证等信息，支持接口信息返回）:http://134.175.148.53:8091/datageneration-Base/\n";

        return result;
    }


}
