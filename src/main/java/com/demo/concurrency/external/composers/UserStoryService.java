package com.demo.concurrency.external.composers;

import com.demo.concurrency.external.models.Album;
import com.demo.concurrency.external.models.Comment;
import com.demo.concurrency.external.models.Photo;
import com.demo.concurrency.external.models.Post;
import com.demo.concurrency.external.models.User;
import com.demo.concurrency.external.models.UserStory;
import com.demo.concurrency.external.rests.AlbumService;
import com.demo.concurrency.external.rests.CommentService;
import com.demo.concurrency.external.rests.PhotosService;
import com.demo.concurrency.external.rests.PostService;
import com.demo.concurrency.external.rests.UserService;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service public class UserStoryService {

    @Autowired
    private Executor executor;

  public Single<UserStory> composeUserStory(
      String userId, PostService postService, UserService userService, CommentService commentService,
      AlbumService albumService, PhotosService photosService) {

      Single<List<Post>> posts = retrieveUserPosts(userId, postService)
              .flatMap(retrieveCommentsInPosts(commentService));

      Single<User> user = retrieveUser(userId, userService);

      Single<List<Album>> albums = retrieveUserAlbumsWithPhotos(userId, albumService, photosService);

      return Single.zip(user, posts, albums, UserStory::new);
  }

    public Single<List<Album>> retrieveUserAlbumsWithPhotos(String userId, AlbumService albumService,
                                                            PhotosService photosService) {

        System.out.printf("retrieveUserAlbumsWithPhotos TreadID = %d  threadName: %s %n", Thread.currentThread().getId(), Thread.currentThread().getName());

        return retrieveUserAlbums(userId, albumService)
            .flatMap(listaAlbumnes -> {

                    List<Single<Album>> albumnes = listaAlbumnes.stream()
                            .map(album ->
                                    retrievePhotos(album.getId(), photosService)
                                    .map(listaPhotos -> new Album(album.getUserId(), album.getId(),
                                            album.getTittle(), listaPhotos))
                            )
                            .collect(Collectors.toList());

                    Flowable<Album> flowableAlbums = Single.merge(albumnes);
                    Single<List<Album>> todosLosAlbums = flowableAlbums.toList();
                    return todosLosAlbums;
            });
    }

  private Function<List<Post>, Single<List<Post>>> retrieveCommentsInPosts(CommentService commentService) {

      System.out.printf("retrieveCommentsInPosts TreadID = %d  threadName: %s %n", Thread.currentThread().getId(), Thread.currentThread().getName());

      return posts -> {
                Flowable<Post>  pormisesPost= Single.merge(
                        posts.stream()
                        .map(post -> retrieveCommentsInPost(commentService, post)
                        .map(comments -> new Post(post.getId(), post.getTitle(), post.getBody(), comments)))
                        .collect(Collectors.toList())
                );

                return pormisesPost.toList();
        };
    }

    private Single<List<Comment>> retrieveCommentsInPost(CommentService commentService, Post post) {

        return Single.fromCallable(() -> {
            System.out.printf("retrieveCommentsInPost TreadID = %d  threadName: %s %n", Thread.currentThread().getId(), Thread.currentThread().getName());
            return commentService.retrieveComments(post.getId());
        }).subscribeOn(Schedulers.from(executor));

    }

    public Single<List<Album>> retrieveUserAlbums(String userId, AlbumService albumService) {

        return Single.fromCallable(() -> {
            System.out.printf("retrieveUserAlbums TreadID = %d  threadName: %s %n", Thread.currentThread().getId(), Thread.currentThread().getName());

            return albumService.retrieveAlbums(userId);
        }).subscribeOn(Schedulers.from(executor));

    }

    public Single<List<Photo>> retrievePhotos(String albumId, PhotosService photosService) {

        return Single.fromCallable(() -> {
            System.out.printf("retrievePhotos TreadID = %d  threadName: %s %n", Thread.currentThread().getId(), Thread.currentThread().getName());

            return photosService.retrievePhotos(albumId);
        }).subscribeOn(Schedulers.from(executor));
    }

    private Single<List<Post>> retrieveUserPosts(String userId, PostService postService) {

        return Single.fromCallable(() -> {
            System.out.printf("retrieveUserPosts TreadID = %d  threadName: %s %n", Thread.currentThread().getId(), Thread.currentThread().getName());
            return postService.retrievePosts(userId);
        }).subscribeOn(Schedulers.from(executor));

    }

    private Single<User> retrieveUser(String userId, UserService userService) {

        return Single.fromCallable(() ->
        {
            System.out.printf("retrieveUser TreadID = %d  threadName: %s %n", Thread.currentThread().getId(), Thread.currentThread().getName());
            return userService.retrieveUser(userId);
        }).subscribeOn(Schedulers.from(executor));

    }

}
