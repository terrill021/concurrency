package com.demo.concurrency.external.rests;

import com.demo.concurrency.external.models.User;
import io.reactivex.Single;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("post")
public interface UserService {

    @GetMapping("/users/{id}")
    Single<User> retrieveUser(@PathVariable String id);
}
