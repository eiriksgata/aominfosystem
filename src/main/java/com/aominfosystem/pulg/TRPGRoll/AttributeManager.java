package com.aominfosystem.pulg.TRPGRoll;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.pulg.TRPGRoll
 * @date:2019/9/16
 **/
public interface AttributeManager {

    public String playerAttributeEntry(String parameter, long fromQQ,long fromGroup);
    public String playerAttributeSet(String parameter,long fromQQ,long fromGroup);
    public String playerAttributeDelete(long fromQQ,long fromGroup);
    public String playerAttributeFindAll(long fromQQ,long fromGroup);
    public String playerAttributeFindByValue(String parameter,long fromQQ,long fromGroup);

}
