package com.zyz.demo.mytools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;

public class dateToformat {
    public static String changedate(String date) {
        String[] temp=date.split("\\s+");
        String tempdate=temp[0];
        String[] parts = tempdate.split("-");
        String month=parts[1];
        String day=parts[2];
        String formatdate=month+"月"+day+"日";
        return formatdate;
    }
    public static String changedatetoyymmdd(String date) {
        String[] temp=date.split("\\s+");
        return temp[0];
    }


}
