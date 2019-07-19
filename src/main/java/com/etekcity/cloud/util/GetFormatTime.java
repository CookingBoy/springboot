package com.etekcity.cloud.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.crypto.Data;

/**
 * 获取格式化的当前时间
 *
 * @author vik
 */
public class GetFormatTime {

    public static String getTime() {
        //格式化的时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        //获取当前时间
        Date date = new Date();
        return sdf.format(date);
    }

    public static String toUtcTime(String stringDate) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = dateFormat.parse(stringDate);
        return simpleDateFormat.format(date);
    }
}
