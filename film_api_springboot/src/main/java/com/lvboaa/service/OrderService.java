package com.lvboaa.service;

import com.lvboaa.pojo.Order;
import com.lvboaa.util.Result;
import com.lvboaa.vo.OrderVo;

import java.util.HashMap;
import java.util.List;

public interface OrderService {
    /**
     * 得到所有分页订单
     * @return
     */
    Result paging(HashMap hashMap);

    /**
     * 删除一个订单
     * @param orderId
     * @return
     */
    int deleteOrder(Integer orderId);

    /**
     * 删除多个订单
     * @param ids
     * @return
     */
    int deleteOrders(String ids);

    /**
     * 新增订单
     * @param hashMap
     * @return
     */
    Object addOrder(HashMap hashMap);

    /**
     * 得到订单信息
     * @param orderId
     * @return
     */
    HashMap getOrderInfo(Integer orderId);

    /**
     * 根据id得到订单信息
     * @param orderId
     * @return
     */
    Order getOrderByOrderId(Integer orderId);

    /**
     * 更新订单
     * @param order
     * @return
     */
    int updateOrder(Order order);

    /**
     * 得到用户的所有订单
     * @param userId
     * @return
     */
    List<OrderVo> getOrderInfoByUserId(Integer userId);
}
