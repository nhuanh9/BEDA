package com.example.datn.controller;

import com.example.datn.model.*;
import com.example.datn.service.CommentService;
import com.example.datn.service.PostService;
import com.example.datn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @GetMapping("/top-4")
    public ResponseEntity<Iterable<Post>> getTop4() {
        Iterable<Post> posts = postService.findTop4New();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Post>> getAll() {
        Iterable<Post> posts = postService.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Iterable<Post>> getAllByCategoryId(@PathVariable Long categoryId) {
        Iterable<Post> linkDocs = postService.findAllByCategoryId(categoryId);
        return new ResponseEntity<>(linkDocs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Post> save(@RequestBody Post post) {
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        post.setCreateAt(date);
        post.setStatus(1);
        post.setLikes((long) 0);
        postService.save(post);
        return new ResponseEntity(post, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable Long id) {
        Post post = postService.findById(id).get();
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Optional<Post>> comment(@PathVariable("id") Long id, @RequestBody Comment commentForm) {
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        commentForm.setCreateAt(date);
        commentService.save(commentForm);
        Optional<Post> postEntity = postService.findById(id);
        if (postEntity.isPresent()) {
            postEntity.get().getListComment().add(commentForm);
            postService.save(postEntity.get());
            return new ResponseEntity<>(postEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
