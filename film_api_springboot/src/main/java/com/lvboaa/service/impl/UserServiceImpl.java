package com.lvboaa.service.impl;

import com.lvboaa.dto.LoginUserDto;
import com.lvboaa.mapper.UserMapper;
import com.lvboaa.pojo.Admin;
import com.lvboaa.pojo.User;
import com.lvboaa.service.UserService;
import com.lvboaa.util.*;
import com.lvboaa.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author lvbo
 * @date 2021年 02月17日 17:56:45
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    private JedisUtils jedisUtils;

    public Result paging(HashMap hashMap) {
        log.debug("分页参数"+hashMap.toString());
        Result result = PageUtils.page(hashMap, userMapper, (a, b)-> b.getAllUser(a), (a, b)->b.getUserCount(a));
        log.debug("分页信息："+result.getData().toString());
        return result;
    }

    public int insertUser(HashMap hashMap) {
        //String birth = ((String) hashMap.get("birthday")).substring(0,10);
        if(hashMap.get("password") != null){
            hashMap.put("password", MD5Utils.md5(hashMap.get("password").toString()));
        }
        log.debug("注册用户:"+hashMap.toString());
        return userMapper.insertUser(hashMap);
    }

    public int updateUser(HashMap hashMap) {
        if(hashMap.get("password") != null){
            hashMap.put("password", MD5Utils.md5(hashMap.get("password").toString()));
        }
        log.debug("更新用户:"+hashMap.toString());
        return userMapper.updateUser(hashMap);
    }

    public int deleteUser(Integer userId) {
        log.debug("删除用户:"+userId);
        return userMapper.deleteUser(userId);
    }

    public int deleteUsers(String ids) {
        List list = CommonUtils.StringArrayToList(ids);
        log.debug("删除多个用户:"+list.toString());
        return userMapper.deleteUsers(list);
    }

    public Result login(LoginUserDto loginUserDto) {
        User user = userMapper.getUserByName(loginUserDto.getUsername());
        if(user != null){
            log.debug("登录用户:"+user.getUserName());
            if(!MD5Utils.verify(loginUserDto.getPassword(),user.getPassword())){
                return new Result(ResultCode.USER_LOGIN_ERROR);
            }else if(user.getState() == 1){
                return new Result(ResultCode.USER_ACCOUNT_FORBIDDEN);
            }
        }else{
            return new Result(ResultCode.USER_LOGIN_ERROR);
        }
        //用户role设置1
        String token = JwtUtils.Sign(user.getUserId(),user.getUserName(),1);
        log.debug("生成token："+token);
        UserVo userVo = new UserVo(user.getUserId(),user.getUserName(),user.getSex(),user.getBirthday(),user.getPhoto(),user.getPhone(),user.getEmail(),token);
        return Result.success(userVo);
    }

    @Override
    public Result adminLogin(LoginUserDto loginUserDto) {
        Admin admin = userMapper.getAdminByName(loginUserDto.getUsername());
        if(admin != null){
            log.debug("登录管理员:"+admin.getAdminName());
            if(!MD5Utils.verify(loginUserDto.getPassword(),admin.getPassword())){
                return new Result(ResultCode.USER_LOGIN_ERROR);
            }
        }else{
            return new Result(ResultCode.USER_LOGIN_ERROR);
        }
        //管理员role设置0
        String token = JwtUtils.Sign(admin.getAdminId(),admin.getAdminName(),0);
        log.debug("生成token："+token);
        admin.setToken(token);
        return Result.success(admin);
    }

    public int stateUser(HashMap hashMap) {
        log.debug("状态改变"+hashMap.get("state"));
        return userMapper.stateUser(hashMap);
    }

    public int checkUser(HashMap hashMap) {
        log.debug("验证用户："+hashMap.toString());
        return userMapper.checkUser(hashMap);
    }

    public User getUserById(Integer userId) {
        log.debug("获取用户：id:"+userId);
        return userMapper.getUserById(userId);
    }

    @Override
    public User getUserByName(String userName) {
        log.debug("获取用户：userName:"+userName);
        return userMapper.getUserByName(userName);
    }

    @Override
    public int updatePhoto(HttpServletRequest request, MultipartFile file){
        //项目target目录
        String path = "";
        try{
            path = ResourceUtils.getURL("classpath:").getPath().substring(1)+ "images/user/";
        }catch (Exception e){
            e.printStackTrace();
        }
        File uploadFile = new File(path);

        String fileName = file.getOriginalFilename();
        log.debug("上传用户头像："+fileName);
        File dir = new File(uploadFile,fileName);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //MultipartFile自带的解析方法
        try {
            file.transferTo(dir);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
