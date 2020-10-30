package com.demo.concurrency.external.rests;

import com.demo.concurrency.external.models.Post;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("post")
public interface PostService {

    @GetMapping("/posts")
    Single<List<Post>> retrievePosts(@RequestParam String userId);
}
