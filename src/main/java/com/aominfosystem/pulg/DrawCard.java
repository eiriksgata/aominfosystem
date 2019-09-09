package com.aominfosystem.pulg;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.pulg.impl
 * @date:2019/9/5
 **/
public interface DrawCard {

    public String drawCardStart(String parameter, long fromqq);
    public String findMyCardBag(String parameter,long fromqq);
    public String useCard(String parameter,long fromqq);
    public String addCard(String parameter,long fromqq);
    public String seeCard(String parameter,long fromqq);

}
