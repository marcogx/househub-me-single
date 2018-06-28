package com.artgeektech.househub.common;

import com.artgeektech.househub.domain.User;

/**
 * Created by guang on 11:45 PM 4/27/18.
 */
public class UserContext {
    private static final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();

    public static void setUser(User user){
        USER_HOLDER.set(user);
    }

    public static void remove(){
        USER_HOLDER.remove();
    }

    public static User getUser(){
        return USER_HOLDER.get();
    }
}
