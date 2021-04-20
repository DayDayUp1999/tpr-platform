package com.zyz.demo.mytools;

import java.util.List;

public class ListToString {

    public static String ToString(List<String> list) {
        System.out.println("list>>>>>"+list);
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        System.out.println("String>>>>>"+result.toString());
        return result.toString();
    }
}
