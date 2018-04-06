package com.yifactory.daocheapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Pattern;

public class ValidationUtil {

    //手机号表达式
    private final static Pattern phone_pattern = Pattern.compile("^(13|15|17|18)\\d{9}$");

    //数字表达式
    private final static Pattern number_pattern = Pattern.compile("^[0-9]*$");

    //银行卡号表达式
    private final static Pattern bankNo_pattern = Pattern.compile("^[0-9]{16,19}$");

    /**
     * 验证手机号是否正确
     * @param phone 手机号码
     * @return boolean
     */
    public static boolean isPhone(String phone) {
        return phone_pattern.matcher(phone).matches();
    }

    /**
     * 验证身份证号码是否正确
     * @param IDCardNo 身份证号码
     * @return boolean
     */
    public static boolean isIDCard(String IDCardNo) {
        String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7","9", "10", "5", "8", "4", "2" };
        String Ai = "";

        if (IDCardNo.length() != 15 && IDCardNo.length() != 18) {
            return false;
        }

        if (IDCardNo.length() == 18) {
            Ai = IDCardNo.substring(0, 17);
        } else if (IDCardNo.length() == 15) {
            Ai = IDCardNo.substring(0, 6) + "19" + IDCardNo.substring(6, 15);
        }
        if (!isNumber(Ai)) {
            return false;
        }

        String strYear = Ai.substring(6, 10);
        String strMonth = Ai.substring(10, 12);
        String strDay = Ai.substring(12, 14);
        if (!getDateIsTrue(strYear, strMonth, strDay)) {
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150 || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e1) {
            e1.printStackTrace();
            return false;
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            return false;
        }

        Hashtable hashtable = getAreaCodeAll();
        if (hashtable.get(Ai.substring(0, 2)) == null) {
            return false;
        }

        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi+ Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;
        if (IDCardNo.length() == 18) {
            if (!Ai.equals(IDCardNo)) {
                return false;
            }
        } else {
            return true;
        }
        return true;
    }

    /**
     * 验证是数字
     * @param str 验证字符
     * @return boolean
     */
    private static boolean isNumber(String str) {
        return number_pattern.matcher(str).matches();
    }

    /**
     * 获取身份证号所有区域编码设置
     * @return Hashtable
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Hashtable getAreaCodeAll() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 检查日期是否有效
     * @param year 年
     * @param month 月
     * @param day 日
     * @return boolean
     */
    private static boolean getDateIsTrue(String year, String month, String day){
        try {
            String data = year + month + day;
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
            simpledateformat.setLenient(false);
            simpledateformat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 身份证号，中间10位星号替换
     *
     * @param id 身份证号
     * @return 星号替换的身份证号
     */
    public static String idHide(String id) {
        return id.replaceAll("(\\d{4})\\d{10}(\\d{4})", "$1**********$2");
    }

    /**
     * 手机号码，中间4位星号替换
     *
     * @param phone 手机号
     * @return 星号替换的手机号
     */
    public static String phoneNoHide(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 根据出生日期计算年龄
     *
     * @param bothData
     * @return
     */
    public static String calculateAge(String bothData) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Date mydate= null;
        try {
            mydate = myFormatter.parse(bothData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000) + 1;

        return new java.text.DecimalFormat("#").format(day / 365f);
    }

    /**
     * 验证是否银行卡号
     * @param bankNo 银行卡号
     * @return
     */
    public static boolean isBankNo(String bankNo){
        //替换空格
        bankNo = bankNo.replaceAll(" ", "");
        //银行卡号可为12位数字
        if(12 == bankNo.length()){
            return true;
        }
        //银行卡号可为16-19位数字
        return bankNo_pattern.matcher(bankNo).matches();
    }

}
