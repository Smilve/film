package com.lvboaa.service;

import com.lvboaa.dto.LoginUserDto;
import com.lvboaa.pojo.User;
import com.lvboaa.util.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author lvbo
 * @date 2021年 02月17日 17:56:33
 */
public interface UserService {

    /**
     * 用户列表分页
     * @param hashMap
     * @return
     */
    Result paging(HashMap hashMap);

    /**
     * 添加一个用户
     * @param hashMap
     * @return
     */
    int insertUser(HashMap hashMap);

    /**
     * 更新用户
     * @param hashMap
     * @return
     */
    int updateUser(HashMap hashMap);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int deleteUser(Integer userId);

    /**
     * 删除多个用户
     * @param ids
     * @return
     */
    int deleteUsers(String ids);
    /**
     * 用户登录
     * @param loginUserDto
     * @return
     */
    Result login(LoginUserDto loginUserDto);

    /**
     * 管理员登录
     * @param loginUserDto
     * @return
     */
    Result adminLogin(LoginUserDto loginUserDto);

    /**
     * 用户解冻/冻结
     * @param hashMap
     * @return
     */
    int stateUser(HashMap hashMap);

    /**
     * 验证用户是否已注册
     * @param hashMap
     * @return
     */
    int checkUser(HashMap hashMap);

    /**
     * 根据用户id得到用户
     * @param userId
     * @return
     */
    User getUserById(Integer userId);

    /**
     * 根据用户名得到用户
     * @param userName
     * @return
     */
    User getUserByName(String userName);

    /**
     * 上传用户头像
     * @param request
     * @param file
     * @return
     */
    int updatePhoto(HttpServletRequest request, MultipartFile file);
}
