package com.etekcity.cloud.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Mac;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import org.apache.commons.lang3.RandomStringUtils;
import com.etekcity.cloud.domain.response.RegisterResponseData;
import com.etekcity.cloud.domain.response.ResponseData;

public class Test {
    public static void main(String[] args) {
        String pattern = "^([a-z0-9]+-[a-z0-9]+-[a-z0-9]+-[a-z0-9]+-[a-z0-9]+)$";
        String content = "63e37b53-cf87-4800-a0a7-a6e5261e4bff";
        boolean isMatch = Pattern.matches(pattern, content);
        int len = content.length();
        String test = RandomStringUtils.randomAlphanumeric(20);
        String test2 = "123 abc";
        String[] str = test2.split(" ");
        //UTC时间
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time1 = sdf1.format(new Date());
        String time2 = sdf2.format(new Date());
        System.out.println(time1 + "\n" + time2);

       /* Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);*/
        System.out.println(isMatch);
    }
}
