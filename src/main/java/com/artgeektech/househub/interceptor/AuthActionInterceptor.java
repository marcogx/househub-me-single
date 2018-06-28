package com.artgeektech.househub.interceptor;

import com.artgeektech.househub.common.ResultMsg;
import com.artgeektech.househub.common.UserContext;
import com.artgeektech.househub.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by guang on 11:15 PM 4/27/18.
 */
@Component
public class AuthActionInterceptor implements HandlerInterceptor {

    // Called before the handler execution, returns a boolean value,
    // “true” : continue the handler execution chain;
    // “false”, stop the execution chain and return it
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        User user = UserContext.getUser();
        if (user == null) {
            String errorMsg  = ResultMsg.error("signin required").asUrlParams();
            String redirectURL = "/accounts/signin?" + errorMsg;
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                String target = URLEncoder.encode(request.getRequestURL().toString(),"utf-8");
                response.sendRedirect(redirectURL + "&target=" + target);
                return false;
            }else {
                response.sendRedirect(redirectURL);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
