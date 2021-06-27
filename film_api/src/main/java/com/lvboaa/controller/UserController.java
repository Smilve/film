package com.lvboaa.controller;

import com.lvboaa.dto.LoginUserDto;
import com.lvboaa.pojo.User;
import com.lvboaa.service.UserService;
import com.lvboaa.util.PageUtils;
import com.lvboaa.util.Result;
import com.lvboaa.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
/**
 * @author lvbo
 * @date 2021年 02月17日 17:58:07
 */

@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(tags = "User", description = "用户")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public String test(){
        return "Hello World!";
    }

    //管理员操作
    @PostMapping("/users")
    @ApiOperation("获取用户列表")
    public Result getAllUser(@RequestBody HashMap<String,Object> hashMap){
        return userService.paging(hashMap);
    }

    @GetMapping("/user/{userId}")
    @ApiOperation("根据用户id得到用户")
    public Result getUserById(@PathVariable("userId") Integer userId){
        User user = userService.getUserById(userId);
        if( user == null){
            return Result.failure();
        }
        return Result.success(user);
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public Result updateUser(@RequestBody HashMap<String,Object> hashMap){

        if(userService.updateUser(hashMap) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @DeleteMapping("/del/{userId}")
    @ApiOperation("删除用户")
    public Result deleteUser(@PathVariable("userId") Integer userId){
        if(userService.deleteUser(userId) == 0){
            return Result.failure();
        }
        return Result.success();
    }

    @PostMapping("/delAll")
    @ApiOperation("删除多个用户")
    public Result deleteUsers(@RequestBody String ids){
        if(userService.deleteUsers(ids) == 0){
            return Result.failure();
        }
        return Result.success();
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login(@RequestBody(required = true) LoginUserDto loginUserDto){
        return userService.login(loginUserDto);
    }

    @PostMapping("/adminLogin")
    @ApiOperation("登录")
    public Result adminLogin(@RequestBody LoginUserDto loginUserDto){
        return userService.adminLogin(loginUserDto);
    }

    @PostMapping("/state")
    @ApiOperation("冻结/恢复")
    public Result stateUser(@RequestBody HashMap hashMap){
        if(userService.stateUser(hashMap) == 0){
            return Result.failure();
        }
        return Result.success();
    }
    @PostMapping("/register")
    @ApiOperation("注册用户")
    public Result insertUser(@RequestBody HashMap<String,Object> hashMap){
        if(userService.insertUser(hashMap)==0){
            return Result.failure();
        }
        return Result.success();
    }
    @PostMapping("/check")
    @ApiOperation("验证用户是否已注册")
    public Result checkUser(@RequestBody HashMap<String,Object> hashMap){
        if(userService.checkUser(hashMap)==1){
            return Result.failure();
        }
        return Result.success();
    }

    @GetMapping("/username/{userName}")
    @ApiOperation("根据用户名得到用户")
    public Result getUserByName(@PathVariable("userName") String userName){
        User user = userService.getUserByName(userName);
        if( user == null){
            return Result.failure();
        }
        return Result.success(user);
    }

    @PostMapping("/photo")
    @ApiOperation("上传用户头像")
    public Result updatePhoto(HttpServletRequest request, MultipartFile file) {
        if(userService.updatePhoto(request,file) == 0){
            return Result.failure();
        }
        return Result.success();
    }
}
