package com.lvboaa.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.lvboaa.config.AlipayConfig;
import com.lvboaa.pojo.Order;
import com.lvboaa.service.AlipayService;
import com.lvboaa.service.OrderService;
import com.lvboaa.util.Result;
import com.lvboaa.vo.OrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author lvbo
 * @date 2021年 02月17日 22:00:33
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
@Slf4j
@Api(tags = "Order", description = "订单")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AlipayService payService;

    @GetMapping("/test")
    @ApiOperation("测试token是否失效")
    public Result test(){
        log.debug("测试token是否失效");
        return Result.success();
    }

    @PostMapping("/orders")
    @ApiOperation("获取订单列表")
    public Result getAllOrder(@RequestBody HashMap hashMap){

        return orderService.paging(hashMap);
    }

    @DeleteMapping("/del/{orderId}")
    @ApiOperation("删除订单")
    public Result deleteOrder(@PathVariable("orderId") Integer orderId){
        if(orderService.deleteOrder(orderId) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @PostMapping("/delAll")
    @ApiOperation("删除多个订单")
    public Result deleteOrders(@RequestBody String ids){
        if(orderService.deleteOrders(ids) == 0){
            return Result.failure();
        }
        return Result.success();
    }

    @PostMapping("/add")
    @ApiOperation("新增订单")
    public Result addOrder(@RequestBody HashMap hashMap){
        Object orderId = orderService.addOrder(hashMap);
        if(orderId == null){
            return Result.failure();
        }
        return Result.success(orderId);
    }

    @GetMapping("/info/{orderId}")
    @ApiOperation("得到订单所有信息")
    public Result getOrderInfo(@PathVariable("orderId") Integer orderId){
        HashMap map = orderService.getOrderInfo(orderId);
        if(map == null){
            return Result.failure();
        }
        return Result.success(map);
    }

    @GetMapping("/order/{orderId}")
    @ApiOperation("得到订单信息")
    public Result getOrderByOrderId(@PathVariable("orderId") Integer orderId){
        Order order = orderService.getOrderByOrderId(orderId);
        if(order == null){
            return Result.failure();
        }
        return Result.success(order);
    }

    @PostMapping("/update")
    @ApiOperation("更新订单状态")
    public Result updateOrder(@RequestBody Order order){
        if(orderService.updateOrder(order)==0){
            return Result.failure();
        }
        return Result.success();
    }

    @GetMapping("/user/{userId}")
    @ApiOperation("得到用户的所有订单")
    public Result getOrderByUserId(@PathVariable("userId") Integer userId){
        List<OrderVo> orderVos = orderService.getOrderInfoByUserId(userId);
        if(orderVos == null){
            return Result.failure();
        }
        return Result.success(orderVos);
    }

    /**【支付宝下单返回付款二维码】**/
    @GetMapping("/pay/{orderId}")
    @ApiOperation("支付宝二维码表单")
    public Result toPay(@PathVariable("orderId") Integer orderId){
        Order order = orderService.getOrderByOrderId(orderId);
        log.debug("阿里支付订单信息：" + order);
        String form = null;
        try {
            form = payService.toPay(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //转换字符串，将html标签转成vue可用的
        String befAction = form;
//        StringBuffer aftAction = new StringBuffer(befAction);
//        aftAction = aftAction.reverse();
//        String midAction = aftAction.substring(68);
//        aftAction = new StringBuffer(midAction).reverse();
//        aftAction=aftAction.append(" width: 200px; margin-top: 20px;background-color:#f03d37;color:white; -moz-border-radius: 10px;  -webkit-border-radius: 10px;  border-radius: 10px;  -khtml-border-radius: 10px;text-align: center;  vertical-align: middle;  border: 1px solid transparent;  font-weight: 900;  font-size:125%\"> </form>");
        return Result.success(form);
    }

    /**
     * 异步通知支付结果
     * @param request
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("/callback")
    @ApiOperation("支付成功回调函数，更新订单状态")
    public String alipayNotify(HttpServletRequest request) throws AlipayApiException {
        log.debug("支付宝支付回调信息");
        String success = "success";
        String failure = "failure";
        //获取支付宝的请求信息
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        // 将 Map<String,String[]> 转为 Map<String,String>
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        params.remove("sign_type");
        // 验签
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
        // 验签通过
        if (signVerified) {
            log.debug("支付宝支付通过验签");
            // 更新订单信息
            String result = payService.updateOrder((HashMap<String, String>) params);
            if ("success".equals(result)) {
                return success;
            }
        }
        return failure;
    }

    @GetMapping("/refund/{orderId}")
    @ApiOperation("退款")
    public Result refund(@PathVariable("orderId")Integer orderId) throws Exception {
        Order order = orderService.getOrderByOrderId(orderId);
        log.debug("阿里退款订单信息：" + order);
        String result = payService.refund(order);
        log.debug(result);
        if (result.toLowerCase().indexOf("success")!=-1){
            Order order1 = new Order();
            order1.setOrderId(orderId);
            order1.setState(3);
            payService.updateOrder1(order1);
            return Result.success();
        }
        return Result.failure();
    }
}
