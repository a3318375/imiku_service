package com.yuxh.blog.contact;

import java.util.HashMap;

public class BaseJsonResult extends HashMap<String, Object> {

    public BaseJsonResult() {
    }

    public static BaseJsonResult success() {
        BaseJsonResult result = new BaseJsonResult();
        result.put("status", true);
        return result;
    }

    public static BaseJsonResult success(String key, Object value) {
        BaseJsonResult result = success();
        result.put(key, value);
        return result;
    }


    public static BaseJsonResult error() {
        return error("系统异常");
    }

    public static BaseJsonResult error(String msg) {
        BaseJsonResult result = new BaseJsonResult();
        result.put("status", false);
        result.put("msg", msg);
        return result;
    }

    @Override
    public BaseJsonResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
