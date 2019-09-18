package com.aominfosystem.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.config
 * @date:2019/9/17
 **/
public class CustomTextConfig {

    public static String attributeEntryMessageSuccess = "attributeEntryMessageSuccess";
    public static String attributeEntryMessageError = "attributeEntryMessageError";
    public static String attributeUpdateMessageSuccess = "attributeUpdateMessageSuccess";
    public static String attributeUpdateMessageError = "attributeUpdateMessageError";
    public static String attributeDeleteMessageSuccess = "attributeDeleteMessageSuccess";
    public static String attributeDeleteMessageError = "attributeDeleteMessageError";
    public static String attributeFindByValueSuccess = "attributeFindByValueSuccess";
    public static String attributeFindByValueError = "attributeFindByValueError";

    public static String rollRaAttributeSuccess = "rollRaAttributeSuccess";
    public static String rollRaAttributeBigSuccess = "rollRaAttributeBigSuccess";
    public static String rollRaAttributeExSuccess = "rollRaAttributeExSuccess";
    public static String rollRaAttributeDifficultySuccess = "rollRaAttributeDifficultySuccess";

    public static String rollRaAttributeFail = "rollRaAttributeFail";
    public static String rollRaAttributeBigFail = "rollRaAttributeBigFail";


    public static String rollRaAttributeError = "rollRaAttributeError";

    public static String rollRSuccess = "rollRSuccess";
    public static String rollRError = "rollRError";

    public static Map<String, String> customText = new HashMap<>();
    static {
        //默认值
        //属性设置
        customText.put(attributeEntryMessageSuccess, "消息录入成功");
        customText.put(attributeEntryMessageError, "消息录入失败,错误原因:{0}");
        customText.put(attributeUpdateMessageSuccess,"修改属性成功");
        customText.put(attributeUpdateMessageError,"修改出错,错误原因:{0}");
        customText.put(attributeDeleteMessageSuccess,"属性删除成功");
        customText.put(attributeDeleteMessageError,"属性删除失败{0}");
        customText.put(attributeFindByValueSuccess,"{0}");
        customText.put(attributeFindByValueError,"查询没有结果");

        //roll 0为 随机数值 1为属性值 2为判断属性名
        customText.put(rollRaAttributeSuccess,"{2}:{0}/{1} ok");
        customText.put(rollRaAttributeBigSuccess,"{2}:{0}/{1} Big ok");
        customText.put(rollRaAttributeExSuccess,"{2}:{0}/{1} Ex ok");
        customText.put(rollRaAttributeDifficultySuccess,"{2}:{0}/{1} Dif ok");

        customText.put(rollRaAttributeFail,"{2}:{0}/{1} fail");
        customText.put(rollRaAttributeBigFail,"{2}:{0}/{1} Big fail");

        //0为错误消息内容，删除掉不会显示处理，你可以将其改为你自定义内容
        customText.put(rollRaAttributeError,"{0}");

        //参数0返回计算过程 1返回公式随机数值
        customText.put(rollRSuccess,"{0}={1}");
        //
        customText.put(rollRError,"{0}");


    }


}
