package com.artgeektech.househub.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by guang on 7:05 PM 4/28/18.
 */
@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {
    @Autowired
    private AuthActionInterceptor authActionInterceptor;

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry
            .addInterceptor(authInterceptor)
            .excludePathPatterns("/static").excludePathPatterns("/error")
            .addPathPatterns("/**");
        registry
            .addInterceptor(authActionInterceptor)
            .addPathPatterns("/accounts/profile").addPathPatterns("/accounts/profileSubmit")
            .addPathPatterns("/house/toAdd").addPathPatterns("/house/bookmarked")
            .addPathPatterns("/house/del").addPathPatterns("/house/ownlist")
            .addPathPatterns("/house/add").addPathPatterns("/house/toAdd")
            .addPathPatterns("/agency/agentMsg").addPathPatterns("/comment/addComment");
        super.addInterceptors(registry);
    }

}
