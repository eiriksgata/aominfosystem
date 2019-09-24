package com.aominfosystem.pulg.TRPGRoll;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.pulg
 * @date:2019/9/16
 **/
public interface RollTheDice {

    //.ra
    public String rollAttribute(String parameter, long fromQQ, long fromGroup);
    //.rc
    public String rollCustomAttribute(String parameter, long fromQQ, long fromGroup);
    //.r
    public String rollRandom(String parameter, long fromQQ, long fromGroup);

    //.sc
    public String rollSCCheck(String parameter, long fromQQ, long fromGroup);

    String rollSHCheck(String parameter, long fromQQ, long fromGroup);


    //.rb
    String rollReward(String parameter, long fromQQ, long fromGroup);

    //.rp
    String rollPunishment(String parameter, long fromQQ, long fromGroup);

    //.rh暗骰
    String rollHide(String parameter, long fromQQ, long fromGroup);
}
