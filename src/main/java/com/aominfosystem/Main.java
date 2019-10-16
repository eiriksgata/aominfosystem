package com.aominfosystem;


import com.aominfosystem.pulg.TRPGRoll.AttributeManagerImpl;
import com.aominfosystem.pulg.TRPGRoll.RollTheDiceImpl;
import com.aominfosystem.vo.HttpClientResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.meowy.cqp.jcq.entity.CQDebug;
import org.meowy.cqp.jcq.entity.CoolQ;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: create by admin
 * @version: v1.0
 * @description: com.aominfosystem
 * @date:2019/8/2
 **/
public class Main {


    /**
     * 用main方法调试可以最大化的加快开发效率，检测和定位错误位置<br/>
     * 以下就是使用Main方法进行测试的一个简易案例
     *
     * @param args 系统参数
     */
    public static void main(String[] args) {


        // 要测试主类就先实例化一个主类对象
        RobotCore demo = new RobotCore(CQDebug.getInstance());
        // 获取当前酷Q操作对象
        CoolQ CQ = demo.getCoolQ();
        CQ.logInfo("[JCQ] TEST Demo", "测试启动");// 现在就可以用CQ变量来执行任何想要的操作了
        // 下面对主类进行各方法测试,按照JCQ运行过程，模拟实际情况
        demo.startup();// 程序运行开始 调用应用初始化方法
        demo.enable();// 程序初始化完成后，启用应用，让应用正常工作


        //System.out.println(CQ.getAppDirectory());
        // 模拟群聊消息
        // 开始模拟群聊消息
        //demo.groupMsg(0, 10008, 123456, 77777, "", ">_musicFind xiami,soldout", 0);
        demo.groupMsg(0, 10008, 783679747, 2353686862L, "", ".r 1d2+2d3", 0);

        // ......
        // 依次类推，可以根据实际情况修改参数，和方法测试效果
        // 以下是收尾触发函数
        // demo.disable();// 实际过程中程序结束不会触发disable，只有用户关闭了此插件才会触发
        demo.exit();// 最后程序运行结束，调用exit方法
    }


}
