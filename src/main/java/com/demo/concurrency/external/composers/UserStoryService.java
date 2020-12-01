package com.demo.concurrency.external.composers;

import com.demo.concurrency.external.models.Album;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service public class UserStoryService {

  public Single<UserStory> composeUserStory(
      String userId, PostService postService, UserService userService, CommentService commentService,
      AlbumService albumService, PhotosService photosService) {

      Single<List<Post>> posts = Single.fromCallable(() -> postService.retrievePosts(userId))
              .flatMap(retrieveCommentsInPosts(commentService));

      Single<User> user = Single.fromCallable(() -> userService.retrieveUser(userId));

      Single<List<Album>> albums = retrieveUserAlbumsWithPhotos(userId, albumService, photosService);

      return Single.zip(user, posts, albums, UserStory::new);
  }

    public Single<List<Album>> retrieveUserAlbumsWithPhotos(String userId, AlbumService albumService,
                                                            PhotosService photosService) {
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


  public Single<List<Album>> retrieveUserAlbums(String userId, AlbumService albumService) {

      return Single.fromCallable(() -> albumService.retrieveAlbums(userId));
  }

  public Single<List<Photo>> retrievePhotos(String albumId, PhotosService photosService) {

      return Single.fromCallable(() -> photosService.retrievePhotos(albumId));
  }

  private Function<List<Post>, Single<List<Post>>> retrieveCommentsInPosts(CommentService commentService) {
        return posts -> {
                Flowable<Post>  pormisesPost= Single.merge(posts.stream()
                .map(post -> Single.fromCallable(() -> commentService.retrieveComments(post.getId()))
                    .map(comments -> new Post(post.getId(), post.getTitle(), post.getBody(), comments)))
                .collect(Collectors.toList()));

                return pormisesPost.toList();
        };
    }
}
