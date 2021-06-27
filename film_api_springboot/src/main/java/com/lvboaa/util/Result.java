package com.lvboaa.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lvbo
 * @date 2021年 02月17日 18:39:08
 */

@Data
@ApiModel("响应数据")
public class Result implements Serializable {
    @ApiModelProperty(value = "响应状态码")
    private Integer code;
    @ApiModelProperty(value = "响应信息")
    private String message;
    @ApiModelProperty(value = "响应数据")
    private Object data;
    public Result(ResultCode resultCode){
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public Result(ResultCode resultCode,Object data){
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    /**
     * 成功无返回数据
     * @return
     */
    public static Result success(){
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 成功有返回数据
     * @return
     */
    public static Result success(Object data){
        return new Result(ResultCode.SUCCESS,data);
    }

    /**
     * 失败无返回数据
     * @return
     */
    public static Result failure(){
        return new Result(ResultCode.ERROR);
    }

    /**
     * 失败有返回数据
     * @return
     */
    public static Result failure(Object data){
        return new Result(ResultCode.ERROR,data);
    }
}
