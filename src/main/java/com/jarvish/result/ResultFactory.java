package com.jarvish.result;

public class ResultFactory {

    public static Result buildSuccessResult(Object data,String message) {
        return buildResult(data,message,StatusCode.SUCCESS.code);
    }

    public static Result buildFailResult(String message) {
        return buildResult(null,message,StatusCode.FAIL.code);
    }


    public static Result buildResult(Object data, String message,Integer resultCode) {
        return new Result(data, message, resultCode);
    }
}
