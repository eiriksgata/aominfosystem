package com.aominfosystem.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public enum MyBatisUtil {

    INSTANCE;
    private static SqlSessionFactory sessionFactory;
    private static ThreadLocal<SqlSession> threadlocal = new ThreadLocal<SqlSession>();

    static {
        String resource = "mybatisConf.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取SqlSessionFactory
     *
     * @return SqlSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        return sessionFactory;
    }

    /**
     * 新建session会话，并把session放在线程变量中
     */
    private static void newSession() {
        // 打开一个session会话
        SqlSession session = sessionFactory.openSession();
        // 将session会话保存在本线程变量中
        threadlocal.set(session);
    }

    /**
     * 返回session对象
     *
     * @return session
     */
    public static SqlSession getSession() {
        //优先从线程变量中取session对象
        SqlSession session = threadlocal.get();
        //如果线程变量中的session为null，
        if (session == null) {
            //新建session会话，并把session放在线程变量中
            newSession();
            //再次从线程变量中取session对象
            session = threadlocal.get();
        }
        return session;
    }

    /**
     * 关闭session对象，并从线程变量中删除
     */
    public static void closeSession() {
        //读取出线程变量中session对象
        SqlSession session = threadlocal.get();
        //如果session对象不为空，关闭sessoin对象，并清空线程变量
        if (session != null) {
            session.close();
            threadlocal.set(null);
        }
    }

}
