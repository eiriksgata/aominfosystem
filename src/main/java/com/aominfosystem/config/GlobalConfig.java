package com.aominfosystem.config;

import org.meowy.cqp.jcq.entity.CoolQ;
import org.meowy.cqp.jcq.message.CQCode;

import java.util.List;

public class GlobalConfig {

    public static Boolean usingLocalData;
    public static String[] userBlacklist;
    public static String[] groupBlacklist;
    public static String[] adminNumberList;
    public static Boolean recordSwitch;
    public static String[] recourdGroupList;
    public static String[] recordAdmin;
    public static String instructionsPrefix = null;

    public static int rollFaceNumber = 100;

    public static int rollHouseRulesNumber = 1;

    public static CoolQ CQ;
    public static CQCode CC;
}
