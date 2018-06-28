package com.artgeektech.househub.interceptor;

import com.artgeektech.househub.common.Constant;
import com.artgeektech.househub.common.UserContext;
import com.artgeektech.househub.domain.User;
import com.google.common.base.Joiner;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by guang on 11:15 PM 4/27/18.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Map<String, String[]> map = request.getParameterMap();
        // save request attribute as populate in .ftl template
        map.forEach((k,v) -> {
            if (k.equals("errorMsg") || k.equals("successMsg") || k.equals("target")) {
                request.setAttribute(k, Joiner.on(",").join(v));
            }
        });
        String reqUri =	request.getRequestURI();
        if (reqUri.startsWith("/static") || reqUri.startsWith("/error") ) {
            return true;
        }
        HttpSession session = request.getSession(true);
        User user =  (User)session.getAttribute(Constant.LOGIN_USER_ATTRIBUTE);
        if (user != null) {
            UserContext.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        UserContext.remove();
    }
}
