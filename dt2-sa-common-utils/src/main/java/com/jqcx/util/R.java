package com.jqcx.util;

import java.util.HashMap;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * @Title: R
 * @author: liuH
 * @create: 2021-09-21 13:45
 * @version：$$Id3312$$
 * @copyright ?2017-2021 Ji Qi Product of the Network Technology Co.,Ltd.All Rights Reserved.
 * @description:
 */
public class  R extends HashMap<String,Object> {

    /** */
    private static final long serialVersionUID = -3661573310929958641L;

    public static final String CODE_TAG = "code";

    public static final String MSG_TAG = "msg";

    public static final String DATA_TAG = "data" ;

    /**
     * 状态类型
     */
    public enum Type {
        /**
         * 成功
         */
        SUCCESS(0),

        /**
         * 错误
         */
        ERROR(1);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    public R() {}
    public R(Type type,String msg) {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
    }
    public R(Type type, String msg,Object data) {
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, msg);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }
    public R(String code, String msg,Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }
    public static R success() {
        return R.success("操作成功");
    }
    public static R success(String msg) {
        return R.success(msg,null);
    }
    public static R success(Object data) {
        return R.success("操作成功",data);
    }
    public static R success(String msg, Object data) {
        return new R(Type.SUCCESS, msg, data);
    }
    /**
     * 返回错误消息
     *
     * @return
     */
    public static R error() {
        return R.error("操作失败");
    }
    public static R error(String msg) {
        return R.error(msg, null);
    }
    public static R error(String msg, Object data) {
        return new R(Type.ERROR, msg, data);
    }
    public static R loginFail(String code,String msg) {
        return loginFail(code, msg, null);
    }
    public static R loginFail(String code,String msg, Object data) {
        return new R(code, msg, data);
    }
    @Override
    public String toString(){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
}
