package com.denny.dream.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/31.
 */
public class Utils {
    //    获取系统时间
    public static String getDataTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }
}
