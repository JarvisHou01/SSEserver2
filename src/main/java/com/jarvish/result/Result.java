package com.jarvish.result;

import lombok.Data;

import java.util.HashMap;

@Data
public class Result {
    private Object data;
    private HashMap<String,Object> meta =new HashMap<>();

    public Result(Object data, String msg, Integer status) {
        meta.put("msg",msg);
        meta.put("status",status);
        this.data = data;
    }
}
