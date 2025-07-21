package com.binninfo.model_train.constant;

import java.text.SimpleDateFormat;

/**
 * @author chen gang
 * @date 2025/2/21
 */
public class DateConstant {
    /**
     * 标准时间格式
     */
    public static final String STANDARD_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 简略标准时间格式
     */
    public static final String SIMPLE_STANDARD_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 一天的时间戳
     */
    public static final long ONE_DAY_MILLIS = 24 * 60 * 60 * 1000L;

    /**
     * 标准时间格式SimpleDateFormat
     */
    public static final SimpleDateFormat STANDARD_SIMPLE_DATE_FORMAT = new SimpleDateFormat(DateConstant.STANDARD_DATE_FORMAT);
    /**
     * 简略时间格式SimpleDateFormat
     */
    public static final SimpleDateFormat SIMPLE_STANDARD_SIMPLE_DATE_FORMAT = new SimpleDateFormat(DateConstant.SIMPLE_STANDARD_DATE_FORMAT);
}
