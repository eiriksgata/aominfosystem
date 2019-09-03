package com.aominfosystem.controller;


import com.aominfosystem.config.CreateSystemFile;
import com.aominfosystem.config.GlobalConfig;
import com.aominfosystem.controller.cofig.InstructionsConfig;
import com.aominfosystem.pulg.DrawUtils;
import com.aominfosystem.pulg.MusicPulg;
import com.aominfosystem.pulg.impl.MusicPulgImpl;
import com.aominfosystem.pulg.impl.NotePulgImpl;
import com.aominfosystem.utils.ConfigurationFile;
import com.aominfosystem.utils.HttpClientUtils;
import com.aominfosystem.utils.RegularExpressionUtils;

import java.io.IOException;
import java.util.*;

import static com.sobte.cqp.jcq.event.JcqApp.CC;
import static com.sobte.cqp.jcq.event.JcqApp.CQ;

public class Instructions {


    private NotePulgImpl notePulg = new NotePulgImpl();
    private MusicPulgImpl musicPulg = new MusicPulgImpl();
    private DrawUtils drawUtils = new DrawUtils();
    private boolean drawCooling = true;
    static boolean recordOpen = false;


    Instructions() {

    }

    /**
     * 指令类型判断
     *
     * @param msg
     * @param fromqq
     * @return
     */
    String directiveJudgment(long fromGroup, String msg, long fromqq) {
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
            //指令关键字判断
            switch (type) {
                case "help":
                    return returnHelpMessage();
                case "link":
                    return returnLinkMessage();
                case "note":
                    return notePulg.noteControl(parameter, fromqq);
                case "noteHelp":
                    return notePulg.returnNotehelpMessage();
                case "noteFind":
                    return notePulg.findNoteList(parameter, fromqq);
                case "noteOpen":
                    return notePulg.openNote(parameter, fromqq);
                case "noteDelete":
                    return notePulg.deleteNote(parameter, fromqq);
                case "draw":
                    return draw(fromGroup, fromqq);
                case "record":
                    return recordManegr(fromqq);
                case "recordHelp":
                    return returnRecordHelpInfo();
                case "get":
                    return httpGetFunction(parameter,fromqq);
                case "repetition":
                    return repetitionMesaage(parameter);
                case "musicPlay":
                    return musicPulg.musicPlay(parameter,fromqq);
                case "musicFind":
                    return musicPulg.musicFind(parameter,fromqq);
                case "musicHelp":
                    return musicPulg.returnMusicHelpMessage();
                default:
                    return "不太清楚您输的什么指令呢";
            }
        } else {
            return null;
        }
    }



    private String repetitionMesaage(String parmeter){
        return parmeter;
    }

    private String httpGetFunction(String parameter,long fromqq){
        String result = "";
        if (GlobalConfig.adminNumberList!=null&&GlobalConfig.adminNumberList.length>0 && GlobalConfig.adminNumberList.length<2){
            try {
                result = HttpClientUtils.doGet(parameter).getContent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            assert GlobalConfig.adminNumberList != null;
            for (int i = 0; i<GlobalConfig.adminNumberList.length; i++){
                if (GlobalConfig.adminNumberList[i].equals(String.valueOf(fromqq))){
                    try {
                        result = HttpClientUtils.doGet(parameter).getContent();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return result;
    }


    private String returnRecordHelpInfo(){

        return "输入>_record后系统将会在后台激活消息记录状态，将会记录接收到的群消息。如果localMessageRecord目录没有那么系统将会创建。" +
                "开启消息记录后，所说的每一句话都会被追加记录在localMessageRecord文件夹中，如果需要单独取出消息内容，或者定期备份，请将该文件重命名即可。" +
                "如果需要关闭请再次输入>_record即可停止消息记录。" +
                "记录按照群号来进行划分。";
    }
    private String recordManegr(long qq) {

        if (GlobalConfig.adminNumberList == null){
            recordOpen = !recordOpen;
        }else {
            for (int i=0;i<GlobalConfig.adminNumberList.length;i++){
                if (GlobalConfig.adminNumberList[i].equals(String.valueOf(qq))){
                    recordOpen = !recordOpen;
                    break;
                }
            }
        }
        return null;
    }

    private String draw(long fromGourp, long fromqq) {

        String filePath = CreateSystemFile.folderName + "\\" + CreateSystemFile.configFileName;
        try {
            String readcool = ConfigurationFile.readCfgValue(filePath, "Draw", "cool", "true");

            Boolean cool = Boolean.valueOf(readcool);
            if (!cool) {
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
            System.out.println(e.toString());
        }

        return null;

    }

    /**
     * 判断消息是否具有指令性质
     *
     * @param msg
     * @return
     */
    private Boolean orderMessageConfirm(String msg) {

        if (msg.length() > 4) {
            return msg.substring(0, 2).equals(InstructionsConfig.prefix);
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
                "\n->_link:链接指令，返回一些链接" +
                "\n*>_noteHelp:笔记帮助功能，里面介绍了具体的各个功能使用方法" +
                "\n>_draw:抽奖指令" +
                "\n->_recordHelp:显示消息记录帮助信息" +
                "\n->_get X:使用HTTP的Get请求，X为访问地址，返回JSON数据" +
                "\n>_musicHelp:详细的介绍了点歌功能的使用" +
                "\n+>_post X:post请求" +
                "\n(带有*的功能需要开启联网才能使用,带有+尚未开放的功能,带有-为主人命令)" +
                "\n当前版本号为 1.0.4 使用>_link查看更新内容";
        return result;
    }

    /**
     * 返回link指令内容
     *
     * @return
     */
    private String returnLinkMessage() {


        String result;
        result = "1.插件后台系统进入操作界面:localhost:5424\n" +
                "2.作者博客网址:https://eiriksgata.home.blog\n" +
                "4.web工具（目前有凯撒密码自动分析工具、任意进制转换）:https://keith404.gitee.io/tool/\n" +
                "5.测试数据生成工具（生成身份证等信息，支持接口信息返回）:http://134.175.148.53:8091/datageneration-Base/\n" +
                "当前版本:1.0.3.RELEASE:";

        return result;
    }


}
