package com.aominfosystem.config;

import com.aominfosystem.utils.ConfigurationFile;

import java.io.IOException;

import static com.aominfosystem.config.CreateSystemFile.createInifile;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.config
 * @date:2019/9/3
 **/
public class ReadLocalConfigFile {
    private String filePath = CreateSystemFile.folderName + "\\" + CreateSystemFile.configFileName;
    public ReadLocalConfigFile(){}
    public void readLocalStart(){
        //检测是否有配置文件,并创建初始配置文件
        createInifile();

        //读取配置文件
        try {
            GlobalConfig.usingLocalData = Boolean.valueOf(ConfigurationFile.readCfgValue(filePath,"AomInfoSystem","usingLocalData","true")) ;
            GlobalConfig.userBlacklist = ConfigurationFile.readCfgValue(filePath,"LocalConfig","userBlacklist",null).split(",");
            GlobalConfig.groupBlacklist = ConfigurationFile.readCfgValue(filePath,"LocalConfig","groupBlacklist",null).split(",");
            GlobalConfig.recordSwitch = Boolean.valueOf(ConfigurationFile.readCfgValue(filePath,"MessageRecord","switch","false"));
            GlobalConfig.recourdGroupList = ConfigurationFile.readCfgValue(filePath,"MessageRecord","recordGroupList","").split(",");
            //获取主人QQ
            GlobalConfig.adminNumberList = ConfigurationFile.readCfgValue(filePath,"LocalConfig","adminNumberList","").split(",");
            //获取指令类型
            GlobalConfig.instructionsPrefix = ConfigurationFile.readCfgValue(filePath,"LocalConfig","instructionsPrefix","1");


            //coc文本载入

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
