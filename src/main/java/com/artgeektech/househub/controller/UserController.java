package com.artgeektech.househub.controller;

import com.artgeektech.househub.common.Constant;
import com.artgeektech.househub.common.ResultMsg;
import com.artgeektech.househub.domain.User;
import com.artgeektech.househub.service.AgencyService;
import com.artgeektech.househub.service.UserService;
import com.artgeektech.househub.utils.HashUtils;
import com.artgeektech.househub.utils.UserHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by guang on 10:27 PM 4/22/18.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AgencyService agencyService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("accounts/register")
    public String register(User account, ModelMap modelMap) {
        // get the register web page.
        if (account == null || account.getName() == null || account.getEmail() == null) {
            modelMap.put("agencyList",  agencyService.getAllAgency());
            return "/user/account/register";
        }
        // validation after user submit the register form
        ResultMsg resultMsg = UserHelper.validate(account);
        if (resultMsg.isSuccess()) {
            try {
                account.setEnable(Constant.NOT_ENABLE);
                userService.addAccount(account);
            } catch (Exception e) {
                resultMsg = ResultMsg.error("database or mail service error");
                return "redirect:/accounts/register?" + resultMsg.asUrlParams();
            }
            modelMap.put("email", account.getEmail());
            return "/user/account/registerSubmit";
        } else {
            return "redirect:/accounts/register?" + resultMsg.asUrlParams();
        }
    }

    @RequestMapping("accounts/verify")
    public String verify(String key) {
        boolean success = userService.enable(key);
        if (success) {
            return "redirect:/accounts/signin?" + ResultMsg.success("activation success").asUrlParams();
        } else {
            return "redirect:/accounts/register?" + ResultMsg.error("activation fail, " +
                "check link broken or expire, please use another email").asUrlParams();
        }
    }

    /**
     * 登录接口
     */
    @RequestMapping("/accounts/signin")
    public String signin(HttpServletRequest req) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String target = req.getParameter("target");
        if (email == null || password == null) {
            req.setAttribute("target", target);
            return "/user/account/signin";
        }
        User user = userService.auth(email, password);
        if (user == null) {
            return "redirect:/accounts/signin?" + "target=" + target + "&email=" + email + "&"
                + ResultMsg.error("email or password wrong").asUrlParams();
        } else if (user.getEnable().equals(Constant.NOT_ENABLE)){
            return "redirect:/accounts/signin?" + "target=" + target + "&email=" + email + "&"
                + ResultMsg.error("activate account required").asUrlParams();
        } else {
            HttpSession session = req.getSession(true);
            userService.setFullAvatarPath(user);
            session.setAttribute(Constant.LOGIN_USER_ATTRIBUTE, user);
            return StringUtils.isNoneBlank(target) ? "redirect:" + target : "redirect:/index";
        }
    }

    /**
     * 登出操作
     *
     * @param request
     * @return
     */
    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }

    // ---------------------个人信息页-------------------------
    /**
     * 1.能够提供页面信息 2.更新用户信息
     *
     * @param updateUser
     * @param
     * @return
     */
    @RequestMapping("accounts/profile")
    public String profile(HttpServletRequest req, User updateUser, ModelMap modelMap) {
        if (updateUser.getEmail() == null) {
            return "/user/account/profile";
        }
        // email is hidden form field, immutable
        User user = userService.updateUser(updateUser, updateUser.getEmail());
        userService.setFullAvatarPath(user);
        req.getSession(true).setAttribute(Constant.LOGIN_USER_ATTRIBUTE, user);
        return "redirect:/accounts/profile?" + ResultMsg.success("update success").asUrlParams();
    }

    @RequestMapping("accounts/changePassword")
    public String changePassword(String email, String password, String newPassword,
                                 String confirmPassword, ModelMap modelMap) {
        User user = userService.auth(email, password);
        if (user == null || !confirmPassword.equals(newPassword)) {
            return "redirect:/accounts/profile?" +
                ResultMsg.error("current password wrong or new password not match").asUrlParams();
        }
        logger.info(user.getAvatar());
        User newUser = userService.findByEmail(email);
        logger.info(newUser.getAvatar());
        newUser.setPasswd(HashUtils.encrypt(newPassword));
        User user2 = userService.save(newUser);
        logger.info(user2.getAvatar());
        return "redirect:/accounts/profile?" + ResultMsg.success("update success").asUrlParams();
    }
}
