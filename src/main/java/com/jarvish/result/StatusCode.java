package com.jarvish.result;

public enum StatusCode {
    SUCCESS(200),
    FAIL(400);
    public int code;

    StatusCode(int code) {
        this.code = code;
    }

}
