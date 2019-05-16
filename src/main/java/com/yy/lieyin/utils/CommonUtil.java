package com.yy.lieyin.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * 常用工具类
 * Copyright © 2019 Shanghai Shangjia Logistics Co., Ltd. All rights reserved.
 *
 * @author Aiai Zhu
 * @date 2019/4/9 11:56
 */
@Component
public class CommonUtil {

    /**
     * 校验固定电话号码
     *
     * @param phoneNumber 固定电话号码
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPhone(String phoneNumber) {
        Pattern p1, p2;
        Matcher m;
        // 验证带区号的
        p1 = compile("^[0][1-9]{2,3}-[0-9]{5,10}$");
        // 验证没有区号的
        p2 = compile("^[1-9]{1}[0-9]{5,8}$");
        if (phoneNumber.length() > 9) {
            m = p1.matcher(phoneNumber);
            return m.matches();
        } else {
            m = p2.matcher(phoneNumber);
            return m.matches();
        }
    }

    /**
     * 根据生日计算年龄
     *
     * @param birthDay 生日(date类型)
     * @return 年龄
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        //当前年份
        int yearNow = cal.get(Calendar.YEAR);
        //当前月份
        int monthNow = cal.get(Calendar.MONTH);
        //当前日期
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //计算整岁数
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                //当前日期在生日之前，年龄减一
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    /**
     * 生成上嘉工号
     *
     * @param roleType 角色类型
     * @return 上嘉工号
     */
    public static String getSjnumber(String roleType, String sjnumber) {

        String newSjnumber = sjnumber.substring(4);
        char[] split = newSjnumber.toCharArray();
        int a = 0;
        for (int i = 0; i < split.length; i++) {
            if ('0' != split[i]) {
                a = i;
                break;
            }
        }
        String substring = newSjnumber.substring(a, newSjnumber.length());
        long b = Long.parseLong(substring) + 1;
        DecimalFormat df = new DecimalFormat("0000000000");
        df.format(b);

        if ("全职".equals(roleType)) {
            return "SJEP" + df.format(b);
        } else if ("实习生".equals(roleType)) {
            return "SJST" + df.format(b);
        } else if ("小时工".equals(roleType)) {
            return "SJPT" + df.format(b);
        } else if ("三方全职".equals(roleType)) {
            return "SJTE" + df.format(b);
        } else {
            return "SJTP" + df.format(b);
        }
    }




    /**
     * list去重
     *
     * @param list
     * @return
     */
    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    /**
     * 格式化价格
     * int 转 BigDecimal
     *
     * @param price
     * @return
     */
    public static Integer formatBigDecimalPriceToInt(BigDecimal price) {
        BigDecimal num = new BigDecimal(100);
        if (price == null) {
            return 0;
        }
        return price.multiply(num).intValue();
    }

    /**
     * 格式化价格
     * BigDecimal 转 int
     *
     * @param price
     * @return
     */
    public static BigDecimal formatIntToBigDecimalPrice(Integer price) {
        BigDecimal bigPrice = new BigDecimal((double) price / 100);
        return bigPrice.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getDateNow() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getDateTimeNow() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 计算年龄
     *
     * @param idCard 身份证号
     * @return 年龄
     */
    public static int getAge(String idCard) {
        String dates;
        if (idCard.length() == 18) {
            dates = idCard.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year = df.format(new Date());
            return Integer.parseInt(year) - Integer.parseInt(dates);
        } else {
            dates = idCard.substring(6, 8);
            return Integer.parseInt(dates);
        }
    }

    /**
     * 获取指定日期的指定月后
     *
     * @param inputDate
     * @param number
     * @return
     * @throws ParseException
     */
    public static String getAfterMonth(String inputDate, int number) throws ParseException {
        //获得一个日历的实例
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;

        //初始日期
        date = sdf.parse(inputDate);

        //设置日历时间
        c.setTime(date);
        //在日历的月份上增加number个月
        c.add(Calendar.MONTH, number);
        //得到你想要得在日历的月份上增加number个月后的日期
        return sdf.format(c.getTime());
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            c.setTime(date);
        }
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * 获取下个月最后一天
     *
     * @return
     */
    public static String getNextMouthLastDate() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 2);
        calendar.set(Calendar.DATE, 0);
        return df.format(calendar.getTime());
    }

    /**
     * 获取当前月第一天
     *
     * @return
     */
    public static String getDatefirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return df.format(calendar.getTime());
    }

    /**
     * 判断文件后缀名是否是传入的值
     *
     * @param file
     * @param type
     * @return
     */
    public static Boolean judgeFileType(MultipartFile file, String type) {
        String fileName = file.getOriginalFilename();
        String suffer = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
        return type.equals(suffer);
    }

    /**
     * post请求外部接口
     *
     * @param date 接收json参数
     * @param url
     * @return json
     */
    public static JSONObject doPost(Object date, String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        // 要调用的接口方法
        HttpPost post = new HttpPost(url);
        JSONObject jsonObject = null;
        try {

            StringEntity s = new StringEntity(date.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            post.addHeader("content-type", "application/json; charset=UTF-8");
            HttpResponse res = client.execute(post);
            String response1 = EntityUtils.toString(res.getEntity());
            jsonObject = JSONObject.parseObject(response1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }
}

