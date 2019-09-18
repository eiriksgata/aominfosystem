package com.aominfosystem.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeTesting {

    public TypeTesting() {
    }

    public boolean isInt(String string) {
        if (string == null)
            return false;

        String regEx1 = "[\\-|\\+]?\\d+";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }
}
