package com.artgeektech.househub.controller;

import com.artgeektech.househub.common.UserContext;
import com.artgeektech.househub.domain.Comment;
import com.artgeektech.househub.domain.User;
import com.artgeektech.househub.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by guang on 2:26 AM 5/17/18.
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment/addComment")
    public String addComment(Comment comment) {
        User user = UserContext.getUser();
        Long userId = user.getId();
        commentService.save(comment, userId);
        if (comment.getHouseId() != null && comment.getHouseId() != 0) {
            return "redirect:/house/detail?id=" + comment.getHouseId();
        } else {
            return "redirect:/agency/agentDetail?id=" + comment.getAgentId();
        }
    }
}
