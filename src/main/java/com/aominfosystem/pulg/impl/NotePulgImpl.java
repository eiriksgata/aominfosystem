package com.aominfosystem.pulg.impl;

import com.aominfosystem.controller.UserController;
import com.aominfosystem.pulg.NotePulg;
import com.aominfosystem.utils.MyBatisUtil;
import com.aominfosystem.mapper.NoteMapper;
import com.aominfosystem.mapper.UserMapper;
import com.aominfosystem.pojo.Note;
import com.aominfosystem.pojo.User;
import com.aominfosystem.utils.TypeTesting;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aominfosystem.utils.RegularExpressionUtils.getMatchers;

public class NotePulgImpl implements NotePulg {

    /**
     * 笔记功能使用
     *
     * @param parameter
     * @param fromqq
     * @return
     */
    public String noteControl(String parameter, long fromqq) {

        //正则表达式
        String regex = "-.*?<";
        String regextow = "<.*?>";
        List<String> instructionsList = getMatchers(regex, parameter);
        List<String> valueList = getMatchers(regextow, parameter);
        //检测通过表init
        Map<String, Boolean> pass = new HashMap<String, Boolean>();
        pass.put("grade", false);
        pass.put("title", false);
        pass.put("hide", true);
        pass.put("text", true);
        User user = new UserController().findUser(fromqq);

        long userGrade = user.getGrade();
        //记录默认值
        Note note = new Note();
        note.setGrade(0);
        note.setCreator(String.valueOf(user.getId()));
        note.setHide("");
        note.setText("");
        note.setTitle("error title");

        String result = "Error Info:";
        for (int i = 0; i < instructionsList.size(); i++) {
            if (instructionsList.get(i).substring(1, instructionsList.get(i).length() - 1).equals("grade")) {
                System.out.println(instructionsList.get(i).substring(1, instructionsList.get(i).length() - 1) + ":" + valueList.get(i).substring(1, valueList.get(i).length() - 1));
                try {
                    if (new TypeTesting().isInt(valueList.get(i).substring(1, valueList.get(i).length() - 1))) {
                        long value = Long.valueOf(valueList.get(i).substring(1, valueList.get(i).length() - 1));
                        if (userGrade >= value && userGrade > 0) {
                            note.setGrade(value);
                            pass.put("grade", true);
                        } else {
                            result += "\n你的权限等级无法设置文本等级";
                        }
                    } else {
                        result += "\ngrade的数值不正确";
                    }

                } catch (Exception e) {
                    System.out.println(e);
                    result += "\n请输入正确的数值范围，请不要尝试玩弄小把戏" + e.toString();
                }
            }
            if (instructionsList.get(i).substring(1, instructionsList.get(i).length() - 1).equals("title")) {
                try {
                    if (valueList.get(i).length() > 2) {
                        note.setTitle(valueList.get(i).substring(1, valueList.get(i).length() - 1));
                        System.out.println(instructionsList.get(i).substring(1, instructionsList.get(i).length() - 1) + ":" + valueList.get(i).substring(1, valueList.get(i).length() - 1));
                        pass.put("title", true);

                    } else {
                        result += "\n标题文本为空,title为必填项";
                    }
                } catch (Exception e) {
                    result += "\n标题输入格式不正确,异常原因有待检测";
                }
            }
            if (instructionsList.get(i).substring(1, instructionsList.get(i).length() - 1).equals("hide")) {
                try {
                    if (valueList.get(i).length() > 2) {
                        note.setHide(valueList.get(i).substring(1, valueList.get(i).length() - 1));
                        System.out.println(instructionsList.get(i).substring(1, instructionsList.get(i).length() - 1) + ":" + valueList.get(i).substring(1, valueList.get(i).length() - 1));
                    } else {
                        note.setHide("");
                    }
                    pass.put("hide", true);
                } catch (Exception e) {
                    result += "隐藏内容录入异常，已终止录入本条信息";
                }

            }
            if (instructionsList.get(i).substring(1, instructionsList.get(i).length() - 1).equals("text")) {
                try {
                    if (valueList.get(i).length() > 2) {
                        note.setText(valueList.get(i).substring(1, valueList.get(i).length() - 1));
                        System.out.println(instructionsList.get(i).substring(1, instructionsList.get(i).length() - 1) + ":" + valueList.get(i).substring(1, valueList.get(i).length() - 1));
                    } else {
                        note.setText("");
                    }
                    pass.put("text", true);

                } catch (Exception e) {
                    result += "文本内容录入异常，已终止信息录入";
                }
            }
        }

        if (pass.get("grade") && pass.get("title") && pass.get("hide") && pass.get("text")) {
            result = "信息录入成功";
            SqlSession sqlSession = MyBatisUtil.getSession();
            NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
            noteMapper.save(note);
            sqlSession.commit();
            sqlSession.close();
        } else {
            result += "\n" + pass.toString();

        }

        return result;
    }

    @Override
    public String deleteNote(String parameter, long fromqq) {
        String result = "";
        if (new TypeTesting().isInt(parameter)) {
            try {
                Integer deleteId = Integer.valueOf(parameter);
                SqlSession sqlSession = MyBatisUtil.getSession();
                NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

                Note note = noteMapper.findByid(deleteId);
                User findUser = userMapper.findByFromqq(fromqq);
                long userGrade;
                if (findUser == null) {
                    userGrade = 0;
                } else {
                    userGrade = findUser.getGrade();
                }

                if (note != null) {
                    if (userGrade >= note.getGrade()) {
                        try {
                            noteMapper.delete(deleteId);
                            sqlSession.commit();
                            result += "删除成功";
                        } catch (Exception e) {

                            result += "删除记录出错，数据库数据异常";
                        }
                    } else {
                        result += "您的权限不够，无法删除该笔记";
                    }

                } else {
                    result += "查询不到该条数据（可能原因：错误的索引号)";
                }

                sqlSession.close();

            } catch (Exception e) {
                System.out.println(e);
                result += "删除出错 具体原因：" + e.toString();
            }
        }
        return result;

    }
    @Override
    public String openNote(String parameter, long fromqq) {
        String result = "";
        if (new TypeTesting().isInt(parameter)) {
            try {
                Integer findId = Integer.valueOf(parameter);
                SqlSession sqlSession = MyBatisUtil.getSession();
                NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
                UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
                Note note = noteMapper.findByid(findId);
                User findUser = userMapper.findByFromqq(fromqq);
                sqlSession.close();
                long userGrade;
                //判断是否是游客用户
                if (findUser == null) {
                    userGrade = 0;
                } else {
                    userGrade = findUser.getGrade();
                }
                if (note != null) {

                    if (userGrade > note.getGrade()) {
                        result += "Note编号:" + note.getId();
                        result += "\n创建人:" + note.getCreator();
                        result += "\n笔记等级:" + note.getGrade();
                        result += "\n标题:" + note.getTitle();
                        result += "\n内容:" + note.getText();
                    } else {
                        result += "您的权限不够打开文本";
                    }
                } else {
                    result += "不好意思，没有这个记录（可能原因：已删除，被隐藏，权限不够，错误编号）";
                }
            } catch (Exception e) {
                System.out.println(e);
                result += "检测到未知错误类型:" + e;
            }

        }
        return result;
    }

    @Override
    public String findNoteList(String parameter, long fromqq) {
        String result = "";
        if (new TypeTesting().isInt(parameter)) {
            Integer startPage = Integer.valueOf(parameter);
            SqlSession sqlSession = MyBatisUtil.getSession();
            NoteMapper noteMapper = sqlSession.getMapper(NoteMapper.class);
            PageHelper.startPage(startPage, 10);
            List<Note> allData = noteMapper.findAll();
            if (allData != null) {
                PageInfo<Note> pageInfo = new PageInfo<Note>(allData);
                result += "索引";
                for (int i = 0; i < pageInfo.getSize(); i++) {
                    result += "\n" + "[" + pageInfo.getList().get(i).getId() + "]" + pageInfo.getList().get(i).getTitle();
                }
            } else {
                result += "当前查询页无数据";
            }
        }
        return result;
    }

    /**
     * 笔记功能介绍
     *
     * @return
     */
    @Override
    public String returnNotehelpMessage() {
        String result = "";
        result = "Note功能介绍:\n" +
                "使用指令>_note\n" +
                "参数设置:\n" +
                "(必填)-grade<X>设置记录等级,默认用户等级为0，根据用户等级查看同等级或以下等级的内容记录（X的值为正整数）\n" +
                "(必填)-title<X>设置记录标题（X为标题的文本）\n" +
                "(选填)-hide<X>密匙，填了常规状态隐藏，要通过查看指令查看（X为密匙）\n" +
                "(选填)-text<X>设置文本内容（X为文本内容,新版本修复了正则表达式文本内容)" +
                "具体使用操作如下:\n" +
                ">_note -grade<0>-title<[This Title]>-text< Hi,welcome >" +
                "\n其他指令:" +
                "\n>_notedelete X:删除指定索引的笔记内容" +
                "\n>_noteopen X:打开note的内容,X为打开的笔记编号值,使用方法>_opennote 2" +
                "\n>_notefind X:查询功能的一种，用于查询笔记索引,X为查询页数,使用方法>_findnote 0,这样返回第一页的内容" +
                "";
        return result;
    }


}
