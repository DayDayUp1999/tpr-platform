package com.zyz.demo.mytools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringtoList {
    public static List<String> ToList(String str) {
        String arr[] = str.split(","); //把这个字符串按"，" 分隔开存入String类型数组d中。
        List<String> Stringlist = Arrays.asList(arr);; //创建一个集合
        System.out.println("转换后StringList："+Stringlist);
        return Stringlist;
    }
}
