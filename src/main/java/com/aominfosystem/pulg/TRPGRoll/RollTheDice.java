package com.aominfosystem.pulg.TRPGRoll;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.pulg
 * @date:2019/9/16
 **/
public interface RollTheDice {

    //.ra
    public String RollAttribute(String parameter, long fromqq, long fromGroup);
    //.rc
    public String RollCustomAttribute(String parameter, long fromqq, long fromGroup);
    //.r
    public String RollRandom(String parameter, long fromqq, long fromGroup);

    //.sc
    public String RollSCCheck(String parameter, long fromqq, long fromGroup);

    String RollSHCheck(String parameter, long fromqq, long fromGroup);



}
