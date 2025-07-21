package com.binninfo.model_train.utils;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {
    /**
     * 空字符串
     */
    private static final String NULLSTR = "";

    /**
     * 校验字符串是否为中文
     */
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    /**
     * 将单个序号转换成指定位数的字符串（前面补0）
     *
     * @param seq 序号
     * @param num 位数
     */
    public static String getSeqNum(int seq, int num) {
        //得到一个NumberFormat的实例
        NumberFormat nf = NumberFormat.getInstance();
        //设置是否使用分组
        nf.setGroupingUsed(false);
        //设置最大整数位数
        nf.setMaximumIntegerDigits(num);
        //设置最小整数位数
        nf.setMinimumIntegerDigits(num);
        return nf.format(seq);
    }


    /**
     * 将日期字符串和时间字符串连接组合成带时间的日期
     *
     * @param dateStr
     * @param hour
     * @return
     */
    public static String getFullDate(String dateStr, String hour) throws Exception {
        String dateyyyyMMdd = dateStr;
        if (!StringUtils.isEmpty(hour)) {
            String dateyyyyMMddhhmmssyyy = dateyyyyMMdd + "0" + hour + "0000000";
            return dateyyyyMMddhhmmssyyy;
        } else {
            return dateyyyyMMdd + "000000000";
        }
    }

    /**
     * 比较时间大小
     *
     * @param date1
     * @param hour
     * @param date2
     * @return
     */
    public static int compareTime(Date date1, int hour, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar1.set(Calendar.HOUR, hour);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.compareTo(calendar2);
    }


    /**
     * 生成短信数字验证码
     *
     * @param num 验证码位数
     * @return
     */
    public static String getKaptcha(int num) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < num; i++) {
            // 生成一个[0,10)
            int randomInt = random.nextInt(10);
            str.append(randomInt);
        }
        return str.toString();
    }

    /**
     * 判断
     *
     * @param hourNum
     * @return
     */
    public static boolean isAfterTime(int hourNum, Date baseTime) throws ParseException {
        //将传入的日期类型转化为固定的格式，以便获取参数时间的毫秒数
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(baseTime);
        //获取参数毫秒数
        long time = sdf.parse(format).getTime();
        //获取系统当前时间的毫秒数
        long l = System.currentTimeMillis();
        //两者相减，获得时间差的毫秒数
        long mills = l - time;
        //将时间差的毫秒数转化成整个的小时数
        int requiredHours = (int) (mills / 1000 / 3600);
        //将所需时间返回
        return requiredHours > hourNum;
    }

    /**
     * 获取32位uuid
     *
     * @return
     */
    public static String getUUID() {
        //转化为String对象
        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");
        return uuid;
    }


    /**
     * * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     *                * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }


    /**
     * * 判断一个对象数组是否非空
     *
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * * 判断一个对象是否是数组类型（Java基本型别的数组）
     *
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 将list转换为字符串
     *
     * @param stringList
     * @param sign       字符串中间的分隔符
     * @return
     */
    public static String listToString(List<String> stringList, String sign) {
        if (stringList.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (String str : stringList) {
            sb.append(str);
            sb.append(sign);
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 去除字符串中间的空格
     *
     * @param str
     * @return
     */
    public static String movespace(String str) {
        str.replaceAll(" +", "");  //去掉所有空格，包括首尾、中间
        return str;
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @return
     */
    public static long getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0, length - 3));
        } else {
            return 0;
        }
    }

    /**
     * 获取字符串中的ip
     *
     * @param str
     * @return
     */
    public static String getThirdIp(String str) {
        Pattern p = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)");
        Matcher m = p.matcher(str);
        //将符合规则的提取出来
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }

    public static String trimBothEndsChars(String srcStr, String splitter) {
        String regex = "^" + splitter + "*|" + splitter + "*$";
        return srcStr.replaceAll(regex, "");
    }


    public static boolean like(String str1, String str2) {
        int index1 = 0;
        int index2 = 0;
        int length1 = str1.length();
        int length2 = str2.length();

        while (index1 < length1 && index2 < length2) {
            char c1 = str1.charAt(index1);
            char c2 = str2.charAt(index2);

            if (c2 == '%') {
                // 处理 %
                while (index2 < length2 && str2.charAt(index2) == '%') {
                    index2++;
                }
                if (index2 >= length2) {
                    // % 在 str2 末尾，返回 true
                    return true;
                }
                while (index1 < length1 && str1.charAt(index1) != str2.charAt(index2)) {
                    index1++;
                }
                if (index1 >= length1) {
                    // 在 str1 中未找到匹配的字符，返回 false
                    return false;
                }
            } else if (c2 == '_') {
                // 处理 _
                index1++;
                index2++;
            } else {
                // 普通字符比较
                if (c1 != c2) {
                    return false;
                }
                index1++;
                index2++;
            }
        }

        // 如果 str2 剩余部分只有 %，则返回 true
        while (index2 < length2 && str2.charAt(index2) == '%') {
            index2++;
        }
        return index1 >= length1 && index2 >= length2;
    }


    public static char numToUpperCase(int num) {
        if (num < 1 || num > 26) {
            throw new IllegalArgumentException("Input number is out of range [1, 26]");
        }
        return (char) ('A' + num - 1);
    }

    public static String formatDouble(int source,int divide){
        if (0==divide){
            return  "0.00%";
        }
        BigDecimal dividend = new BigDecimal(source);
        BigDecimal divisor = new BigDecimal(divide);
        BigDecimal rateResult = dividend.divide(divisor, 4, BigDecimal.ROUND_HALF_UP);
        return String.format("%.2f",rateResult.doubleValue()*100)+"%";
    }


    public static List<String> getFileContent(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";
        List<String> result = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            result.add(line);
        }
        reader.close();
        return  result;
    }
}
