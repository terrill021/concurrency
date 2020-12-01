package com.demo.concurrency.web.rest;

import com.demo.concurrency.external.composers.UserStoryService;
import com.demo.concurrency.external.models.UserStory;
import com.demo.concurrency.external.rests.AlbumService;
import com.demo.concurrency.external.rests.CommentService;
import com.demo.concurrency.external.rests.PhotosService;
import com.demo.concurrency.external.rests.PostService;
import com.demo.concurrency.external.rests.UserService;
import io.reactivex.Single;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class UserPostsRestService {

    private final UserStoryService userStoryService;
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;
    private final AlbumService albumService;
    private final PhotosService photosService;

    public UserPostsRestService(UserStoryService userStoryService, CommentService commentService,
                                PostService postService, UserService userService, AlbumService albumService,
                                PhotosService photosService) {
        this.userStoryService = userStoryService;
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
        this.albumService = albumService;
        this.photosService = photosService;
    }

    @GetMapping("users/{userId}")
    Single<UserStory> retrieveUserAndPosts(@PathVariable String userId) {
        return userStoryService.composeUserStory(userId, postService, userService, commentService, albumService, photosService);
    }

}
