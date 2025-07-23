package com.apiserverapplication.backend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiserverapplication.backend.model.Post;
import com.apiserverapplication.backend.model.PostRequest;
import com.apiserverapplication.backend.service.PostService;

import java.util.List;
@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequest request,
                                             @RequestHeader("Authorization") String auth) {
        return postService.createPost(request, auth);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable int id,
                                           @RequestHeader("Authorization") String auth) {
        return postService.likePost(id, auth);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable int id,
                                             @RequestHeader("Authorization") String auth) {
        return postService.deletePost(id, auth);
    }

    @GetMapping
    public ResponseEntity<List<Post>> listPosts(@RequestHeader("Authorization") String auth) {
        return postService.listPosts(auth);
    }
}
