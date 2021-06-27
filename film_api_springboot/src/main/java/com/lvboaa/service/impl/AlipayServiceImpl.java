package com.lvboaa.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.lvboaa.config.AlipayConfig;
import com.lvboaa.mapper.OrderMapper;
import com.lvboaa.pojo.Order;
import com.lvboaa.service.AlipayService;
import com.lvboaa.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author lvbo
 * @date 2021年 05月03日 22:09:34
 */
@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public String toPay(Order order) throws Exception{
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getOrderId().toString();
        //付款金额，必填
        String total_amount = String.valueOf(order.getPrice());
        //订单名称，必填
        String subject = "橘子电影";
        //商品描述，可空
        String body = "";

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "15m";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

//        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
//                + "\"total_amount\":\""+ total_amount +"\","
//                + "\"subject\":\""+ subject +"\","
//                + "\"body\":\""+ body +"\","
//                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String form = "";
        AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest);
        if (response.isSuccess()) {
            form = alipayClient.pageExecute(alipayRequest).getBody();
        }
        // 这里返回的 form 是一个字符串，里面封装了支付的表单信息
        //（即 html 标签 和 javascript 代码），直接将这个 form 输出到页面即可。
        return form;
    }

    @Override
    public String updateOrder(HashMap<String,String> params) {
        if (params == null || params.isEmpty()){
            return "success";
        }
        String orderId = params.get("out_trade_no");
        String tradeStatus = params.get("trade_status");
        // 支付成功
        if ("TRADE_SUCCESS".equals(tradeStatus)){
            log.debug("订单支付成功service");
            // 更新订单信息
            Order order = new Order();
            order.setOrderId(Integer.parseInt(orderId));
            order.setState(1);
            order.setCode(CommonUtils.genCodes(6));
            orderMapper.updateOrder(order);
            log.debug("订单更新成功");
            return "success";
        }
        return "failure";
    }

    @Override
    public int updateOrder1(Order order) {
        log.debug("退款更新用户："+order.toString());
        return orderMapper.updateOrder(order);
    }

    //退款
    @Override
    public String refund(Order order) throws Exception {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        //商户订单号，必填
        String out_trade_no = order.getOrderId().toString();
        //需要退款的金额，该金额不能大于订单金额，必填
        String refund_amount = order.getPrice().toString();
        //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
        String out_request_no = UUID.randomUUID().toString();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"refund_amount\":\""+ refund_amount +"\","
                + "\"out_request_no\":\""+ out_request_no +"\"}");
        //请求
        String result = alipayClient.execute(alipayRequest).getBody();
        return result;
    }
}
