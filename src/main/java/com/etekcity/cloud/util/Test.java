package com.etekcity.cloud.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String strDate = dateTimeFormatter.format(localDateTime);
        System.out.println(strDate);
    }
}
