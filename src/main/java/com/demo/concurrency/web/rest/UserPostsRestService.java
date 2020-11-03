package com.demo.concurrency.web.rest;

import com.demo.concurrency.external.composers.UserStoryService;
import com.demo.concurrency.external.models.UserStory;
import com.demo.concurrency.external.rests.CommentService;
import com.demo.concurrency.external.rests.PostService;
import com.demo.concurrency.external.rests.UserService;
import io.reactivex.Single;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users/")
public class UserPostsRestService {

    private final UserStoryService userStoryService;
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public UserPostsRestService(UserStoryService userStoryService, CommentService commentService, PostService postService, UserService userService) {
        this.userStoryService = userStoryService;
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("{userId}")
    Single<UserStory> retrieveUserAndPosts(@PathVariable String userId) {
        return userStoryService.composeUserService(userId, postService, userService, commentService);
    }
}