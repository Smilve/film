package com.lvboaa.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

/**
 * @author lvbo
 * @date 2021年 02月19日 19:10:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageUtils implements Serializable {
    private static final long serialVersionId = 1;
    //总记录数
    private int totalCount;
    //每页记录数
    private int pageSize;
    //总页数
    private int totalPage;
    //当前页数
    private int currPage;
    //列表数据
    private List<?> list;

    /**
     * java8函数式接口的应用
     * @param hashMap
     * @param mapper 使用的mapper
     * @param getList 调用的方法 得到数据
     * @param getCount 调动的方法 得到计数
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T,U> Result page(HashMap hashMap, U mapper, BiFunction<HashMap, U, List<T>> getList,
                                  BiFunction<HashMap, U, Integer> getCount){
        //计算每页的开始index
        int pageSize = Integer.parseInt(hashMap.get("pageSize").toString());
        int pageIndex = Integer.parseInt(hashMap.get("pageIndex").toString());
        hashMap.put("startIndex",(pageIndex-1)*pageSize);

        List<T> list = getList.apply(hashMap,mapper);
        int count = getCount.apply(hashMap, mapper);
        if (list == null){
            return Result.failure();
        }
        PageUtils page = new PageUtils(count,pageSize,count/pageSize+1,pageIndex,list);
        return Result.success(page);
    }
}
