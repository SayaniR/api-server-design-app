package com.apiserverapplication.backend.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.apiserverapplication.backend.model.Post;
import com.apiserverapplication.backend.model.PostRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class PostService {

	private final Map<String, List<Post>> userPosts = new ConcurrentHashMap<>();
    private final Map<Integer, Post> postStore = new ConcurrentHashMap<>();
    private int postIdCounter = 1;
    private static final Logger logger = LogManager.getLogger(PostService.class);

    public ResponseEntity<String> createPost(PostRequest req, String auth) {
        String user = AuthService.getUserFromToken(auth);
        if (user == null) {
            logger.warn("Unauthorized attempt to create post");
            return unauthorized();
        }

        Post post = new Post();
        post.setId(postIdCounter++);
        post.setUsername(user);
        post.setContent(req.getContent());
        post.setLikes(new HashSet<>());

        userPosts.putIfAbsent(user, new ArrayList<>());
        userPosts.get(user).add(post);
        postStore.put(post.getId(), post);
        logger.info("Post created by {}: ID {}", user, post.getId());
        return ResponseEntity.ok("Post created with ID: " + post.getId());
    }

    public ResponseEntity<String> likePost(int id, String auth) {
        String user = AuthService.getUserFromToken(auth);
        if (user == null) {
            logger.warn("Unauthorized attempt to like post");
            return unauthorized();
        }

        Post post = postStore.get(id);
        if (post == null) {
            logger.warn("Post not found: ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
        post.getLikes().add(user);
        return ResponseEntity.ok("Post liked");
    }

    public ResponseEntity<String> deletePost(int id, String auth) {
        String user = AuthService.getUserFromToken(auth);
        if (user == null) {
            logger.warn("Unauthorized attempt to delete post");
            return unauthorized();
        }
        Post post = postStore.get(id);
        if (post == null || !post.getUsername().equals(user)) {
            logger.warn("Delete denied. User: {}, Post ID: {}", user, id);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cannot delete this post");
        }
        postStore.remove(id);
        userPosts.get(user).remove(post);
        return ResponseEntity.ok("Post deleted");
    }

    public ResponseEntity<List<Post>> listPosts(String auth) {
        String user = AuthService.getUserFromToken(auth);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        logger.info("Listing all posts for user: {}", user);
        return ResponseEntity.ok(new ArrayList<>(postStore.values()));
    }

    private ResponseEntity<String> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }
}
