package com.liang.androidopencv;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.jetbrains.annotations.NotNull;

public class JsonFactory {
    @NotNull
    public static <T> T parseObject(@NotNull String tesT_JSON) {
        return JSON.parseObject(tesT_JSON,new TypeReference<T>(){});
    }
}
