package com.artgeektech.househub.service;

import com.artgeektech.househub.domain.Comment;
import com.artgeektech.househub.domain.User;
import com.artgeektech.househub.repository.CommentRepository;
import com.artgeektech.househub.repository.UserRepository;
import com.artgeektech.househub.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by guang on 2:32 AM 5/17/18.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.prefix}")
    private String imgPrefix;

    @Transactional(rollbackFor = Exception.class)
    public Comment save(Comment comment, Long userId) {
        User user = userRepository.findOne(userId);
        comment.setAvatar(user.getAvatar());
        comment.setUserName(user.getName());
        BeanHelper.setDefaultProp(comment, Comment.class);
        BeanHelper.onInsert(comment);
        return commentRepository.save(comment);
    }

    public List<Comment> getHouseComments(Long houseId, int sizeLimit) {
        List<Comment> comments = commentRepository.getHouseComments(houseId, sizeLimit);
        setFullAvatarPath(comments);
        return comments;
    }

    public List<Comment> getAgentComments(Long agentId, int sizeLimit) {
        List<Comment> comments = commentRepository.getAgentComments(agentId, sizeLimit);
        setFullAvatarPath(comments);
        return comments;
    }

    private void setFullAvatarPath(List<Comment> comments) {
        comments.forEach(comment -> comment.setAvatar(imgPrefix + comment.getAvatar()));
    }
}
