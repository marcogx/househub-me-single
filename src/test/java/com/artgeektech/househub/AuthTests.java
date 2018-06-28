package com.artgeektech.househub;

import com.artgeektech.househub.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by guang on 12:22 PM 4/29/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthTests {

    private Logger logger = LoggerFactory.getLogger(AuthTests.class);

    @Autowired
    private UserService userService;

    @Test
    public void testAuth() {
        assert 1 == 1;
    }
}
