package com.artgeektech.househub.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * Created by guang on 12:44 AM 4/26/18.
 */
public class HashUtils {
    private static final HashFunction FUNCTION = Hashing.md5();
    private static final String SALT = "09vu8ycmcl7pn";

    public static String encrypt(String input){
        HashCode hashCode =	FUNCTION.hashString(input + SALT, Charset.forName("UTF-8"));
        return hashCode.toString();
    }
}
