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

    public static String rollRandomSuccess = "rollRandomSuccess";
    public static String rollRandomError = "rollRandomError";

    //check
    public static String rollSCCheckSuccess = "rollSCCheckSuccess";
    public static String rollSCCheckFail = "rollSCCheckFail";
    public static String rollSCCheckBigFail = "rollSCCheckBigFail";
    public static String rollSCCheckError = "rollSCCheckError";

    //.sh
    public static String rollSHCheckSuccess = "rollSHCheckSuccess";
    public static String rollSHCheckError = "rollSHCheckError";
    public static String rollSHCheckHPSuccess = "rollSHCheckHPSuccess";

    //.rb
    public static String rollRbBigSuccess = "rollRbBigSuccess";
    public static String rollRbExSuccess = "rollRbExSuccess";
    public static String rollRbDifficultySuccess = "rollRbDifficultySuccess";
    public static String rollRbSuccess = "rollRbSuccess";
    public static String rollRbFail = "rollRbFail";
    public static String rollRbBigFail = "rollRbBigFail";
    public static String rollRbError = "rollRbError";

    //.rp
    public static String rollRpBigSuccess = "rollRpBigSuccess";
    public static String rollRpExSuccess = "rollRpExSuccess";
    public static String rollRpDifficultySuccess = "rollRpDifficultySuccess";
    public static String rollRpSuccess = "rollRpSuccess";
    public static String rollRpFail = "rollRpFail";
    public static String rollRpBigFail = "rollRpBigFail";
    public static String rollRpError = "rollRpError";

    //.rh
    public static String rollRhSuccess = "rollRhSuccess";
    public static String rollRhPrivateSuccess = "rollRhPrivateSuccess";
    public static String rollRhError = "rollRhError";
    public static Map<String, String> customText = new HashMap<>();
    static {
        //默认值
        //属性设置
        customText.put(attributeEntryMessageSuccess, "{member.getCard}消息录入成功{0}");
        customText.put(attributeEntryMessageError, "消息录入失败,错误原因:{0}");
        customText.put(attributeUpdateMessageSuccess,"{member.getCard}修改属性成功");
        customText.put(attributeUpdateMessageError,"{member.getCard}修改出错,错误原因:{0}");
        customText.put(attributeDeleteMessageSuccess,"属性删除成功");
        customText.put(attributeDeleteMessageError,"属性删除失败{0}");
        customText.put(attributeFindByValueSuccess,"{0}");
        customText.put(attributeFindByValueError,"查询没有结果");

        //roll 0为 随机数值 1为属性值 2为判断属性名 3投骰人
        //ra
        customText.put(rollRaAttributeSuccess,"{member.getCard} {2}:{0}/{1} ok {3}");
        customText.put(rollRaAttributeBigSuccess,"{member.getCard} {2}:{0}/{1} {3}Big ok");
        customText.put(rollRaAttributeExSuccess,"{member.getCard} {2}:{0}/{1} {3}Ex ok");
        customText.put(rollRaAttributeDifficultySuccess,"{member.getCard} {2}:{0}/{1} {3}Dif ok");

        customText.put(rollRaAttributeFail,"{member.getCard} {2}:{0}/{1} {3}fail");
        customText.put(rollRaAttributeBigFail,"{member.getCard} {2}:{0}/{1} {3}Big fail");

        //0为错误消息内容，删除掉不会显示处理，你可以将其改为你自定义内容
        customText.put(rollRaAttributeError,"{0}");

        //参数0返回计算过程 1返回公式随机数值
        customText.put(rollRandomSuccess,"{member.getCard}:{0}={1}");
        //计算出错，返回出错内容
        customText.put(rollRandomError,"{0}");

        //check 0投出点数 1自身数值 2投出的公式 3投出结果 4剩余数量
        customText.put(rollSCCheckSuccess,"{member.getCard} {0}/{1} ok 减少公式{2} = {3} 剩余:{4} {5} ");
        customText.put(rollSCCheckFail,"{member.getCard} {0}/{1} fail 减少公式{2} = {3} 剩余:{4} {5} ");
        customText.put(rollSCCheckBigFail,"{member.getCard} {0}/{1} big oh no 减少公式{2} = {3} 剩余:{4} {5}");
        customText.put(rollSCCheckError,"{member.getCard}输入正确的指令:{0}");

        //.sh
        customText.put(rollSHCheckSuccess,"{member.getCard} 属性{0} 你有{1}  {2} = {3} ok ");
        customText.put(rollSHCheckError,"{member.getCard} {0} error");
        customText.put(rollSHCheckHPSuccess,"{member,getCard} hp:{0}  {1} = {2} ok");

        //.rb
        customText.put(rollRbSuccess,"{member.getCard} {0} {1}  {2}  {3} {4} ok rb");
        customText.put(rollRbBigSuccess,"{member.getCard} {0} {1}  {2}  {3} {4} big ok rb" );
        customText.put(rollRbExSuccess,"{member.getCard} {0} {1}  {2}  {3} {4} ex ok rb");
        customText.put(rollRbDifficultySuccess,"{member.getCard} {0} {1}  {2}  {3} {4} difficulty ok rb");
        customText.put(rollRbBigFail,"{member.getCard} {0} {1}  {2}  {3} {4} fail rb");
        customText.put(rollRbFail,"{member.getCard} {0} {1}  {2}  {3} {4} big fail rb");
        customText.put(rollRbError,"{member.getCard} {0} error");

        //.rp
        customText.put(rollRpSuccess,"{member.getCard} {0} {1}  {2}  {3} {4} ok rp");
        customText.put(rollRpBigSuccess,"{member.getCard} {0} {1}  {2}  {3} {4} big ok rp");
        customText.put(rollRpExSuccess,"{member.getCard} {0} {1}  {2}  {3} {4}Ex ok rp");
        customText.put(rollRpDifficultySuccess,"{member.getCard} {0} {1}  {2}  {3} {4} difficulty ok rp");
        customText.put(rollRpBigFail,"{member.getCard} {0} {1}  {2}  {3} {4} big fail rp");
        customText.put(rollRpFail,"{member.getCard} {0} {1}  {2}  {3} {4} fail rp");
        customText.put(rollRpError,"{member.getCard} {0} error");

        //rh
        customText.put(rollRhSuccess,"{member.getCard} ok");
        customText.put(rollRhPrivateSuccess,"在{0} ({member.getGroupId})中{member.getCard} 骰出了: D100= {1}");
        customText.put(rollRhError,"{member.getCard} {0} error");

    }


}
