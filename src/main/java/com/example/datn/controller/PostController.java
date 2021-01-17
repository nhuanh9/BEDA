package com.example.datn.controller;

import com.example.datn.model.Image;
import com.example.datn.model.Post;
import com.example.datn.model.User;
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

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<Post>> getAll() {
        Iterable<Post> posts = postService.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity addPost(@RequestBody Post post) {
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        post.setCreateAt(date);
        post.setStatus(1);
        post.setLikes((long) 0);
        postService.save(post);
        return new ResponseEntity(HttpStatus.OK);
    }
}