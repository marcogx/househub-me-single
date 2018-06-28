package com.artgeektech.househub.utils;

import com.artgeektech.househub.common.ResultMsg;
import com.artgeektech.househub.domain.User;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by guang on 9:23 PM 4/25/18.
 */
public class UserHelper {
    public static ResultMsg validate(User account) {
        if (StringUtils.isBlank(account.getEmail())) {
            return ResultMsg.error("Email blank");
        }
        if (StringUtils.isBlank(account.getName())) {
            return ResultMsg.error("Name blank");
        }
        if (StringUtils.isBlank(account.getPasswd()) || StringUtils.isBlank(account.getConfirmPasswd())) {
            return ResultMsg.error("Password blank");
        }
        if (!account.getPasswd().equals(account.getConfirmPasswd())) {
            return ResultMsg.error("Password not match");
        }
        if (account.getPasswd().length() < 6) {
            return ResultMsg.error("Password too short");
        }
        return ResultMsg.success();
    }
}
