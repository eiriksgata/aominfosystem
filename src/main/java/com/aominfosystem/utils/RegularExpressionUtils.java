package com.aominfosystem.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionUtils {

    /**
     * 正则表达式返回内容
     *
     * @param regex
     * @param source
     * @return
     */
    public static List<String> getMatchers(String regex, String source) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }


    public static String getMatcherString(String regex, String source) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        matcher.find();
        return matcher.group();
    }
}
