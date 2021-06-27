package com.lvboaa.util;

import com.lvboaa.mapper.CommetMapper;
import com.lvboaa.vo.CommetDto;
import lombok.extern.slf4j.Slf4j;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 电影推荐
 * @author lvbo
 * @date 2021年 05月10日 10:22:40
 */
@Component
@Slf4j
public class MovieRecommend {

    @Autowired
    CommetMapper commetMapper;

    private List<Integer> movieList;
    private List<Integer> userList;
    Map<String,List<CommetDto>> map;
    private DenseMatrix64F userDM;
    private DenseMatrix64F scoreDM;
    private DenseMatrix64F result;

    public List<Integer> getMovieById(Integer userId,int num){
        int rn = getResult(userId);
        if (rn == -1){
            return null;
        }
        int n=userList.indexOf(userId);
        HashMap<Integer, Double> map1 = new HashMap<>();
        for (int i=0;i<movieList.size();i++){
            map1.put(movieList.get(i), this.result.get(i,n));
        }
        for (List<CommetDto> list1: map.values()){
            if (list1.get(0).getUserId()==userId){
                for (CommetDto u:list1){
                    if (map1.containsKey(u.getMovieId())){
                        map1.remove(u.getMovieId());
                    }
                }
            }
        }
        Map<Integer, Double> map2 = CommonUtils.sortByKey(map1, true);
        List<Integer> list2= new ArrayList<>();
        if (map2.keySet().size() <=num){
            for (Integer key:map2.keySet()){
                list2.add(key);
            }
        }else{
            int i=0;
            for (Integer key:map2.keySet()){
                list2.add(key);
                ++i;
                if (i==num){
                    break;
                }
            }
        }
        log.debug("推荐电影结果："+list2.toString());
        return list2;
    }

    /**
     * 计算结果矩阵
     * @return
     */
    public int getResult(Integer userId){
        List<CommetDto> list = commetMapper.getAllCommetDto();
        log.debug("推荐电影："+list.toString());
        userList = new ArrayList<>(list.stream().map(u->u.getUserId()).collect(Collectors.toSet()));
        if (userList.indexOf(userId) == -1){
            return -1;
        }
        movieList = new ArrayList<>(list.stream().map(u -> u.getMovieId()).collect(Collectors.toSet()));
        Collections.sort(userList, new Mycomparator());
        Collections.sort(movieList, new Mycomparator());
        map = new LinkedHashMap<>();
        for (int i=0;i<userList.size();i++){
            int n=i;
            List<CommetDto> list1 = list.stream().filter(u -> u.getUserId() == userList.get(n)).collect(Collectors.toList());
            map.put("list"+(i+1),list1);
        }
        getUserDM();
        getScoreDM();
        result = new DenseMatrix64F(movieList.size(), userList.size());
        CommonOps.mult(userDM,scoreDM,result); //矩阵相乘
        return 0;
    }

    /**
     * 将同时喜欢物品x1和x2的用户数组成矩阵，共线矩阵
     * @return
     */
    public void getUserDM() {
        userDM = new DenseMatrix64F(movieList.size(), movieList.size());
        for (int i = 0; i < movieList.size(); i++) {
            for (int j = 0; j < movieList.size(); j++) {
                int n = 0;
                int b = movieList.get(i);
                int c = movieList.get(j);
                for(List<CommetDto> list: map.values()){
                    if (list.stream().filter(m -> m.getMovieId() == b).collect(Collectors.toList()).size() > 0
                            && list.stream().filter(m -> m.getMovieId() == c).collect(Collectors.toList()).size() > 0) {
                        n++;
                    }
                }
                userDM.add(i, j, n);
            }
        }
    }

    /**
     * 生成用户评分矩阵
     * @return
     */
    public DenseMatrix64F getScoreDM(){
        scoreDM = new DenseMatrix64F(movieList.size(), userList.size());
        for (int i = 0; i < movieList.size(); i++) {
            for (int j = 0; j < userList.size(); j++) {
                int n = 0;
                int b = movieList.get(i);
                int c = userList.get(j);
                double data=0.0;
                for(List<CommetDto> list: map.values()){
                    if (list.get(0).getUserId() == c){
                        List<Double> ls = list.stream().filter(u -> u.getUserId() == c && u.getMovieId() == b)
                                .map(u -> u.getScore()).collect(Collectors.toList());
                        if (ls.size() == 0){
                            data=0.0;
                        }else {
                            data = Double.parseDouble(ls.get(0).toString());
                        }
                    }
                }
                scoreDM.add(i,j,data);
            }
        }
        return scoreDM;
    }

    public static void main(String[] args) {
        ArrayList<CommetDto> list = new ArrayList<>();
        list.add(new CommetDto(1,102, 0.1));
        list.add(new CommetDto(1,103,0.2));
        list.add(new CommetDto(2,101,0.1));
        list.add(new CommetDto(3,102,0.5));
        list.add(new CommetDto(3,104,0.7));
        list.add(new CommetDto(1,104,0.3));
        list.add(new CommetDto(2,102,0.7));
        list.add(new CommetDto(2,105,0.9));
        list.add(new CommetDto(3,105,0.5));
//        MovieRecommend movieRecommend = new MovieRecommend();
//        movieRecommend.getResult(list);
//        System.out.println(movieRecommend.getMovieById(1, 1));
    }

}

/**
 * 降序排序
 */
class Mycomparator implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        return (int)o1-(int)o2;
    }
}