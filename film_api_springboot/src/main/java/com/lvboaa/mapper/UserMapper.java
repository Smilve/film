package com.lvboaa.mapper;

import com.lvboaa.pojo.Admin;
import com.lvboaa.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    /**
     * 获取所有用户
     * @return
     */
    List<User> getAllUser(HashMap hashMap);

    /**
     * 用户总数
     * @return
     */
    int getUserCount(HashMap hashMap);

    /**
     * 新增用户
     * @param hashMap
     * @return
     */
    int insertUser(HashMap hashMap);

    /**
     * 更新用户信息
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
     * @param list
     * @return
     */
    int deleteUsers(List list);
    /**
     * 通过用户名得到用户信息
     * @param userName
     * @return
     */
    User getUserByName(String userName);

    /**
     * 通过用户名得到管理员信息
     * @param adminName
     * @return
     */
    Admin getAdminByName(String adminName);
    /**
     * 根据用户id得到用户
     * @param userId
     * @return
     */
    User getUserById(Integer userId);

    /**
     * 用户解冻/冻结
     * @param hashMap
     * @return
     */
    int stateUser(HashMap hashMap);

    /**
     * 通过用户名判断是否有用户
     * @param hashMap
     * @return
     */
    int checkUser(HashMap hashMap);
}
