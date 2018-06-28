package com.artgeektech.househub.service;

import com.artgeektech.househub.common.HouseUserType;
import com.artgeektech.househub.domain.HouseUser;
import com.artgeektech.househub.domain.User;
import com.artgeektech.househub.repository.HouseUserRepository;
import com.artgeektech.househub.repository.UserRepository;
import com.artgeektech.househub.utils.BeanHelper;
import com.artgeektech.househub.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by guang on 1:17 AM 4/20/18.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @Autowired
    private HouseUserRepository houseUserRepository;

    @Value("${file.prefix}")
    private String imgPrefix;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Integer deleteByEmail(String email) {
        return userRepository.deleteByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean enable(String key) {
        return mailService.enable(key);
    }

    /**
     * 1.插入数据库，非激活;密码加盐md5;保存头像文件到本地 2.生成key，绑定email 3.发送邮件给用户
     *
     * @param account
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void addAccount(User account) {
        account.setPasswd(HashUtils.encrypt(account.getPasswd()));
        account.setAvatar(fileService.getImgPath(account.getAvatarFile()));
        BeanHelper.setDefaultProp(account, User.class);
        BeanHelper.onInsert(account);
        save(account);
        mailService.registerNotify(account.getEmail());
    }

    public User auth(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPasswd().equals(HashUtils.encrypt(password))) {
            return user;
        } else {
            return null;
        }
    }

    public User updateUser(User updateUser, String email) {
        User user = findByEmail(email);
        user.setName(updateUser.getName());
        user.setPhone(updateUser.getPhone());
        user.setAboutMe(updateUser.getAboutMe());
        BeanHelper.onUpdate(user);
        userRepository.save(user);
        return user;
    }

    public User setFullAvatarPath(User user) {
        user.setAvatar(imgPrefix + user.getAvatar());
        return user;
    }

    public User findOne(Long id) {
        return setFullAvatarPath(userRepository.findOne(id));
    }

    public User findAgentByHouse(Long houseId) {
        HouseUser houseUser = houseUserRepository.findByHouseIdAndType(houseId, HouseUserType.SALE.value);
        return findOne(houseUser.getUserId());
    }

}
