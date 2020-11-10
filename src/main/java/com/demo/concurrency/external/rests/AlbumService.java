package com.demo.concurrency.external.rests;

import com.demo.concurrency.external.models.Album;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("post")
public interface AlbumService {

    @GetMapping("/albums")
    List<Album> retrieveAlbums(@RequestParam String userId);
}
