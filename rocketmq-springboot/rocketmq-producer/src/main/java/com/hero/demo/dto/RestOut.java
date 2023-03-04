package com.hero.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * JSON返回结果。
 *
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RestOut<T>
{

    /**
     * 成功
     */
    public static final int STATUS_SUCCESS = 0;

    /**
     * 失败
     */
    public static final int STATUS_ERROR = -1;
    /**
     * 状态
     */
    @JsonProperty("respCode" )
    private int respCode;

    /**
     * 消息
     */
    @JsonProperty("respMsg" )
    private String respMsg;


    /**
     * 实际的数据
     */
    @JsonProperty("datas" )
    private T datas;


    public RestOut(@JsonProperty("respCode" ) int respCode,
                   @JsonProperty("respMsg" ) String respMsg,
                   @JsonProperty("datas" ) T datas)
    {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.datas = datas;
    }


    /**
     * 重载的  构造者 方法
     */
    public static <T> RestOut<T> build(int status, String msg, T data)
    {
        return new RestOut<T>(status, msg, data);
    }

    /**
     * 封装成功的返回数据
     *
     * @return 封装结果
     */
    public static <T> RestOut<T> success(T data)
    {
        return build(STATUS_SUCCESS, "请求成功", data);
    }

    public static <T> RestOut<T> success(T data, String message)
    {
        return build(STATUS_SUCCESS, message, data);
    }

    /**
     * 返回错误消息
     *
     * @return 封装结果
     */
    public static <T> RestOut<T> error(String message)
    {
        return build(STATUS_ERROR, message, null);
    }

    public static <T> RestOut<T> error(int status, String message)
    {
        return build(status, message, null);
    }

    public static <T> RestOut<T> failed(String errMsg)
    {
        return build(STATUS_ERROR, errMsg, null);
    }

    public static <T> RestOut<T> succeed(String message)
    {
        return build(STATUS_SUCCESS, message, null);
    }

    public RestOut<T> setRespMsg(String respMsg)
    {
        this.respMsg = respMsg;
        return this;
    }


    public RestOut<T> setRespCode(int respCode)
    {
        this.respCode = respCode;
        return this;
    }

    @Override
    public String toString()
    {
        return "RestOut{" +
                "datas=" + datas +
                ", respCode=" + respCode +
                ", respMsg='" + respMsg +
                '}';
    }


    public boolean isSuccess()
    {
        return respCode==STATUS_SUCCESS;
    }
}
