package com.lvboaa.mapper;

import com.lvboaa.pojo.Order;
import com.lvboaa.vo.OrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface OrderMapper {

    /**
     * 按条件获取所有订单
     * @return
     */
    List<Order> getAllOrder(HashMap hashMap);

    /**
     * 获取订单总数
     * @param hashMap
     * @return
     */
    int getOrderCount(HashMap hashMap);

    /**
     * 新增订单
     * @param hashMap
     * @return
     */
    int insertOrder(HashMap hashMap);

    /**
     * 更新订单
     * @param order
     * @return
     */
    int updateOrder(Order order);

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    int deleteOrder(Integer orderId);

    /**
     * 删除多个订单
     * @param list
     * @return
     */
    int deleteUsers(List list);

    /**
     * 根据订单id得到订单信息
     * @param orderId
     * @return
     */
    Order getOrderById(Integer orderId);

    /**
     * 根据订单的排片id得到电影院、电影、影厅名和其他信息
     * @param scheduleId
     * @return
     */
    OrderVo getOrderInfoByScheduleId(Integer scheduleId);

    /**
     * 根据用户得到所有的订单
     * @param userId
     * @return
     */
    List<OrderVo> getOrderInfoByUserId(Integer userId);

    /**
     * 根据电影id得到累计票房
     * @param movieId
     * @return
     */
    Integer getAllPriceByMovieId(Integer movieId);
}
