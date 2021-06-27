package com.lvboaa.service.impl;

import com.lvboaa.mapper.OrderMapper;
import com.lvboaa.pojo.Order;
import com.lvboaa.service.OrderService;
import com.lvboaa.util.CommonUtils;
import com.lvboaa.util.PageUtils;
import com.lvboaa.util.Result;
import com.lvboaa.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 02月17日 21:59:21
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public Result paging (HashMap hashMap) {
        log.debug("分页参数"+hashMap.toString());
        Result result = PageUtils.page(hashMap, orderMapper, (a, b)-> b.getAllOrder(a), (a, b)->b.getOrderCount(a));
        log.debug("分页信息："+result.getData().toString());
        return result;
    }

    public int deleteOrder(Integer orderId) {
        log.debug("删除订单："+orderId);
        return orderMapper.deleteOrder(orderId);
    }

    public int deleteOrders(String ids) {
        List list = CommonUtils.StringArrayToList(ids);
        log.debug("删除多个订单:"+list.toString());
        return orderMapper.deleteUsers(list);
    }

    @Override
    public Object addOrder(HashMap hashMap) {
        hashMap.put("state",0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        hashMap.put("date",simpleDateFormat.format(new Date()).toString());
        log.debug("新增订单:"+hashMap.toString());
        orderMapper.insertOrder(hashMap);
        return hashMap.get("orderId");
    }

    @Override
    public HashMap getOrderInfo(Integer orderId) {
        log.debug("得到订单信息："+orderId);
        HashMap<Object, Object> map = new HashMap<>();
        Order order = orderMapper.getOrderById(orderId);
        map.put("order",order);
        map.put("orderVo",orderMapper.getOrderInfoByScheduleId(order.getScheduleId()));
        return map;
    }

    @Override
    public Order getOrderByOrderId(Integer orderId) {
        log.debug("根据id得到订单："+orderId);
        return orderMapper.getOrderById(orderId);
    }

    @Override
    public int updateOrder(Order order) {
        log.debug("更新订单信息"+order.toString());
        return orderMapper.updateOrder(order);
    }

    @Override
    public List<OrderVo> getOrderInfoByUserId(Integer userId) {
        log.debug("得到所有订单:"+userId);
        return orderMapper.getOrderInfoByUserId(userId);
    }

}
