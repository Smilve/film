package com.lvboaa.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvboaa.pojo.User;
import com.lvboaa.util.ConstantUtils;
import com.lvboaa.util.JwtUtils;
import com.lvboaa.util.Result;
import com.lvboaa.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lvbo
 * @date 2021年 02月19日 13:58:15
 */
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //axios在发送请求之前需要先发送一个OPTIONS预请求 相当于请求两次，直接过滤就好
        if ("OPTIONS".equals(request.getMethod().toString())) {
            return true;
        }

        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String uri = request.getRequestURI();
        log.debug("请求"+uri);
        if ("/order/callback".equals(uri) || "/order/refund".equals(uri)){
            return true;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        String token = request.getHeader("Authorization");

        //返回错误参数
        Result result = new Result(ResultCode.TOKEN_VERIFY_ERROR);
        String errorJson = new ObjectMapper().writeValueAsString(result);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        if(token==null){
            response.getWriter().println(errorJson);
            return false;
        }

        log.debug("验证："+token);
        User user = JwtUtils.parseJwt(token);
        if(user!=null){
            log.debug("角色:"+user.getRole());
            if(user.getRole() == 0){
                for (String adminUri : ConstantUtils.SUPER_ADMIN_URI) {
                    if(uri.indexOf(adminUri) != -1){
                        return true;
                    }
                }
            }else{
                for (String userUri : ConstantUtils.ADMIN_URI) {
                    if(uri.indexOf(userUri) != -1){
                        return true;
                    }
                }
            }
            response.getWriter().println(new ObjectMapper().writeValueAsString(new Result(ResultCode.AUTH_ERROR)));
            return false;
        }
        response.getWriter().println(errorJson);
        return false;
    }
}
