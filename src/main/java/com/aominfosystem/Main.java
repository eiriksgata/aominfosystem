package com.aominfosystem;


import com.aominfosystem.json.searchbean.SearchBeanVo;
import com.aominfosystem.pulg.TRPGRoll.AttributeManagerImpl;
import com.aominfosystem.pulg.TRPGRoll.RollTheDiceImpl;
import com.aominfosystem.utils.HttpClientUtils;
import com.aominfosystem.vo.HttpClientResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.aominfosystem.pulg.TRPGRoll.RollTheDiceImpl.formulaCalculation;

/**
 * @author: create by admin
 * @version: v1.0
 * @description: com.aominfosystem
 * @date:2019/8/2
 **/
public class Main {


    public static void main(String args[]) throws IOException {

//        AttributeManagerImpl attributeManager = new AttributeManagerImpl();
//        //String result = attributeManager.playerAttributeEntry("1力量65str65敏捷70dex70意志45pow45体质75con75外貌70app70教育55edu55体型55siz55智力70灵感70int70san32san值32理智32理智值32幸运35运气35mp9魔法9hp13体力13会计5人类学1估价5考古学1魅惑15攀爬20计算机5计算机使用5电脑5信用20信誉20信用评级20克苏鲁0克苏鲁神话0cm0乔装5闪避80汽车20驾驶20汽车驾驶20电气维修10电子学1话术5斗殴80手枪71急救66历史5恐吓15跳跃20英语1德语1日语1母语55法律5图书馆20图书馆使用20聆听80开锁1撬锁1锁匠1机械维修10医学60博物学10自然学10领航10导航10神秘学5重型操作1重型机械1操作重型机械1重型1说服10精神分析1心理学10骑术5生物学1药学1妙手10侦查60潜行60生存10游泳20投掷20追踪10驯兽5潜水1爆破1读唇1催眠1炮术1",14561,125678);
//        String result;
//        result=attributeManager.playerAttributeFindByValue("san",1456,125678);
//
//        System.out.println(result);

        RollTheDiceImpl rollTheDice = new RollTheDiceImpl();
//        for (int i = 0; i < 100; i++) {
//            System.out.println(rollTheDice.RollAttribute("力量", 1456, 125678));
//
//        }

        System.out.println(rollTheDice.RollRandom("1d2+1d3-1d5*(5+1d3)",123,123));
    }


}
