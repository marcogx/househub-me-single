package com.artgeektech.househub.common;

import com.google.common.base.Joiner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guang on 9:22 PM 4/25/18.
 */
public class ResultMsg {
    public static final String errorMsgKey = "errorMsg";

    public static final String successMsgKey = "successMsg";

    private String errorMsg;

    private String successMsg;

    public boolean isSuccess(){
        return errorMsg == null;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public static ResultMsg error(String msg) {
        if (msg == null || msg.length() == 0) {
            throw new IllegalArgumentException("Error message must be not blank");
        }
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setErrorMsg(msg);
        return resultMsg;
    }

    public static ResultMsg success(String msg) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setSuccessMsg(msg);
        return resultMsg;
    }

    public static ResultMsg success() {
        return success("OK");
    }

    public String asUrlParams() {
        Map<String, String> map = new HashMap<>();
        if (successMsg != null) {
            map.put(successMsgKey, successMsg);
        }
        if (errorMsg != null) {
            map.put(errorMsgKey, errorMsg);
        }
        map.forEach((k, v) -> {
            if (v != null) {
                try {
                    map.put(k, URLEncoder.encode(v, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(map);
    }
}
