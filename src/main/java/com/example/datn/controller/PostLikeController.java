package com.example.datn.controller;


import com.example.datn.model.Post;
import com.example.datn.model.PostLike;
import com.example.datn.model.User;
import com.example.datn.service.PostLikeService;
import com.example.datn.service.PostService;
import com.example.datn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/post-likes")
public class PostLikeController {
    @Autowired
    PostLikeService postLikeService;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    private boolean checkLike(User user, Post postEntity, Iterable<PostLike> postLikes) {
        for (PostLike i : postLikes) {
            if (i.getPostEntity() == postEntity && i.getUser() == user && i.isLiked()) {
                return false;
            }
        }
        return true;
    }

    @GetMapping
    public ResponseEntity<Iterable<PostLike>> getAll() {
        Iterable<PostLike> postLikes = postLikeService.findAll();
        return new ResponseEntity<>(postLikes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PostLike>> getById(@PathVariable Long id) {
        Optional<PostLike> postLike = this.postLikeService.findById(id);
        if (!postLike.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postLike, HttpStatus.OK);
    }

    @GetMapping("/{user-id}/{post-id}")
    public ResponseEntity<PostLike> getByUserIdAndPostId(@PathVariable("user-id") Long userId, @PathVariable("post-id") Long postId) {
        PostLike postLike = this.postLikeService.findByUserIdAndPostEntityId(userId, postId);
        return new ResponseEntity<>(postLike, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<PostLike> postLike = this.postLikeService.findById(id);
        if (!postLike.isPresent()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        postLikeService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity add(@RequestBody PostLike postLike) {
//        postLikeService.save(postLike);
//        return new ResponseEntity(HttpStatus.CREATED);
//    }

    @PutMapping("/{id}")
    public ResponseEntity edit(@RequestBody PostLike postLike, @PathVariable Long id) {
        Optional<PostLike> postLike1 = this.postLikeService.findById(id);
        if (!postLike1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postLike.setId(postLike1.get().getId());
        postLikeService.save(postLike);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity like(@RequestBody PostLike like) {
        Post postEntity = postService.findById(like.getPostEntity().getId()).get();
        User user = userService.findById(like.getUser().getId()).get();
        PostLike lastLike = postLikeService.findByUserIdAndPostEntityId(user.getId(), postEntity.getId());
        if (checkLike(user, postEntity, postLikeService.findAll())) {
            if (lastLike == null) {
                like.setPostEntity(postEntity);
                like.setLiked(true);
                postLikeService.save(like);
            } else {
                lastLike.setLiked(true);
                postLikeService.save(lastLike);
            }
            Long oldLikes = postEntity.getLikes();
            oldLikes = oldLikes == null ? Long.valueOf(0) : oldLikes;
            postEntity.setLikes(oldLikes + Long.valueOf(1));
            postService.save(postEntity);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/unlike")
    public ResponseEntity unlike(@RequestBody PostLike like) {
        Post postEntity = postService.findById(like.getPostEntity().getId()).get();
        User user = userService.findById(like.getUser().getId()).get();
        PostLike lastLike = postLikeService.findByUserIdAndPostEntityId(user.getId(), postEntity.getId());
        if (!checkLike(user, postEntity, postLikeService.findAll())) {
            lastLike.setLiked(false);
            Long oldLikes = postEntity.getLikes();
            oldLikes = oldLikes == null ? Long.valueOf(0) : oldLikes;
            postEntity.setLikes(oldLikes - Long.valueOf(1));
            postService.save(postEntity);
            postLikeService.save(lastLike);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }


}
