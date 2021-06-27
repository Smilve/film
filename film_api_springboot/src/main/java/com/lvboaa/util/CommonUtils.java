package com.lvboaa.util;


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 简单的工具类
 * @author lvbo
 * @date 2021年 02月24日 12:46:59
 */
public class CommonUtils {
    /**
     * 得到当前时间
     * @return
     */
    public static String getCurrentTime(){
        return TimeStampToTime(System.currentTimeMillis());
    }
    /**
     * 时间戳转换成时间
     * @param t
     * @return
     */
    public static String TimeStampToTime(Long t){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(Long.parseLong(String.valueOf(t))));
    }

    /**
     * 得到当前时间的之前几天
     * @param day
     * @return
     */
    public static String getNowMinusDayTime(int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - day);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        System.out.println(result);
    }

    /**
     * 字符串数组('[1,2,3]')转换成list
     * @param ids
     * @return
     */
    public static List StringArrayToList(String ids){
        String[] id = ids.substring(1,ids.length()-1).split(",");
        List<Integer> list = new ArrayList();
        for (int i = 0; i < id.length; i++) {
            list.add(Integer.parseInt(id[i]));
        }
        return list;
    }

    /**
     *  String数组转换成list
     * @param key
     * @return
     */
    public static List arrayToList(String... key){
        return Arrays.asList(key);
    }


    /**
     * 生成指定位数的数字+字母的字符串
     * @param length
     * @return
     */
    public static String genCodes(int length){
        List<String> results=new ArrayList<String>();
        String val = "";
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字
            if("char".equalsIgnoreCase(charOrNum)) {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            }
            else if("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        val=val.toLowerCase();
        return val;
    }

    /**
     *
     * 根据java8的流式计算排序
     * 根据map的key排序
     *
     * @param map 待排序的map
     * @param isDesc 是否降序，true：降序，false：升序
     * @return 排序好的map
     * @author zero 2019/04/08
     */
    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map, boolean isDesc) {
        Map<K, V> result = new LinkedHashMap();
        if (isDesc) {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey().reversed())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        } else {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }
        return result;
    }

    /**
     * 根据map的value排序
     *
     * @param map 待排序的map
     * @param isDesc 是否降序，true：降序，false：升序
     * @return 排序好的map
     * @author zero 2019/04/08
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean isDesc) {
        Map<K, V> result = new LinkedHashMap();
        if (isDesc) {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue().reversed())
                    .forEach(e -> result.put(e.getKey(), e.getValue()));
        } else {
            map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue())
                    .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        }
        return result;
    }
}
