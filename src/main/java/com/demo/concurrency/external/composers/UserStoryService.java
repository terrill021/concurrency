package com.demo.concurrency.external.composers;

import com.demo.concurrency.external.models.Post;
import com.demo.concurrency.external.models.User;
import com.demo.concurrency.external.models.UserStory;
import com.demo.concurrency.external.rests.CommentService;
import com.demo.concurrency.external.rests.PostService;
import com.demo.concurrency.external.rests.UserService;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service public class UserStoryService {

  public Single<UserStory> composeUserService(
      String userId, PostService postService, UserService userService, CommentService commentService) {

      Single<List<Post>> posts = Single.fromCallable(() -> postService.retrievePosts(userId))
              .flatMap(retrieveCommentsInPosts(commentService));

      Single<User> user = Single.fromCallable(() -> userService.retrieveUser(userId));

      return Single.zip(user, posts, UserStory::new);
  }

    private Function<List<Post>, Single<List<Post>>> retrieveCommentsInPosts(CommentService commentService) {
        return posts -> Single.merge(posts.stream()
                .map(post -> Single.fromCallable(() -> commentService.retrieveComments(post.getId()))
                    .map(comments -> new Post(post.getId(), post.getTitle(), post.getBody(), comments)))
                .collect(Collectors.toList())).toList();
    }
}
