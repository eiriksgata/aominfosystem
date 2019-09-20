package com.aominfosystem.pulg.TRPGRoll;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.pulg
 * @date:2019/9/16
 **/
public interface RollTheDice {

    //.ra
    public String rollAttribute(String parameter, long fromqq, long fromGroup);
    //.rc
    public String rollCustomAttribute(String parameter, long fromqq, long fromGroup);
    //.r
    public String rollRandom(String parameter, long fromqq, long fromGroup);

    //.sc
    public String rollSCCheck(String parameter, long fromqq, long fromGroup);

    String rollSHCheck(String parameter, long fromqq, long fromGroup);



}
