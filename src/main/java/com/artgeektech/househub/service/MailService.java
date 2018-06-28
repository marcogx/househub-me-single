package com.artgeektech.househub.service;

import com.artgeektech.househub.common.Constant;
import com.artgeektech.househub.domain.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by guang on 2:03 AM 4/27/18.
 */
@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${domain.name}")
    private String domainName;

    private Logger logger = LoggerFactory.getLogger(MailService.class);

    private final Cache<String, String> registerCache =
        CacheBuilder.newBuilder().maximumSize(100)
            .expireAfterAccess(15, TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String, String>() {
                {
                    logger.debug("Removal Listener created");
                }
                @Override
                public void onRemoval(RemovalNotification<String, String> removalNotification) {
                    String email = removalNotification.getValue();
                    logger.info("on removal for email" + email);
                    // 代码优化: 在删除前首先判断用户是否已经被激活，对于未激活的用户进行移除操作
                    User registerAccount = userService.findByEmail(email);
                    logger.info("on removal for user" + registerAccount);
                    if (registerAccount != null
                        && registerAccount.getEnable().equals(Constant.NOT_ENABLE)) {
                        int res = userService.deleteByEmail(email);
                        logger.info("deleted: " + res);
                    }
                }
            }).build();


    /**
     * 1.缓存key-email的关系 2.借助spring mail 发送邮件 3.借助异步框架进行异步操作
     *
     * @param email
     */
    @Async
    public void registerNotify(String email) {
        String randomKey = RandomStringUtils.randomAlphabetic(10);
        registerCache.put(randomKey, email);
        logger.info("registerNotify for entry:  " + randomKey + "  " + registerCache.getIfPresent(randomKey));
        String url = "http://" + domainName + "/accounts/verify?key=" + randomKey;
        String text = "Thank you for registering at HouseHub. " +
            "Please click the link to activate your account: ";
        sendMail("HouseHub Activation Email", email, text + url);
    }

    @Async
    public void sendMail(String subject, String toEmail, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject(subject);
        message.setTo(toEmail);
        message.setText(text);
        mailSender.send(message);
    }

    public boolean enable(String key) {
        String email = registerCache.getIfPresent(key);
        if (StringUtils.isBlank(email)) {
            return false;
        }
        User registerAccount = userService.findByEmail(email);
        registerAccount.setEnable(Constant.ENABLE);
        userService.save(registerAccount);
        registerCache.invalidate(key);
        return true;
    }
}
