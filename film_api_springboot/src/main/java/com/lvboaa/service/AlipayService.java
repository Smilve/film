package com.lvboaa.service;

import com.lvboaa.pojo.Order;

import java.util.HashMap;

public interface AlipayService {
    String toPay(Order order) throws Exception;

    String updateOrder(HashMap<String, String> hashMap);

    int updateOrder1(Order order);

    String refund(Order order) throws Exception;
}
