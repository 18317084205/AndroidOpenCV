package com.liang.androidopencv;

import com.alibaba.fastjson.JSON;
import org.jetbrains.annotations.NotNull;

public class Test {
    //{"code":0,"msg":"测试一次","data":{"studentName":"lily","studentAge":12}}
    public static final String TEST_JSON = "{\"code\":0,\"msg\":\"测试一次\",\"data\":{\"studentName\":\"lily\",\"studentAge\":12}}";

    public int code;
    public String msg;
    public String data;

    @NotNull
    public <T> T parseObject(Class<T> tClass) {
        return JSON.parseObject(data, tClass);
    }


}
