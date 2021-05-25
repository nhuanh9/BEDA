package com.example.datn.controller;

import com.example.datn.model.*;
import com.example.datn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;

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
    ICategoryService categoryService;
    @Autowired
    CommentService commentService;
    @Autowired
    private PostLikeService postLikeService;

    @GetMapping("/top-4")
    public ResponseEntity<Iterable<Post>> getTop4() {
        Iterable<Post> posts = postService.findTop4Likes();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/order-by-likes")
    public ResponseEntity<Iterable<Post>> getAllByLikes() {
        Iterable<Post> posts = postService.findAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<Post>> getAll() {
        Iterable<Post> posts = postService.findAllOrderByCreateAt();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<Iterable<Post>> getAllAdminPost() {
        Iterable<Post> posts = postService.findAllAdminPost();
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
        post.setLikes((long) 0);
        postService.save(post);
        User user = post.getUser();
        Long oldPosts = user.getPosts();
        oldPosts = oldPosts == null ? Long.valueOf(0) : oldPosts;
        user.setPosts(oldPosts + Long.valueOf(1));
        userService.save(user);
        return new ResponseEntity(post, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> get(@PathVariable Long id) {
        Post post = postService.findById(id).get();
        List<Comment> listCmt = post.getListComment();
        try {
            Collections.sort(listCmt, new Comparator<Comment>() {
                @Override
                public int compare(Comment o1, Comment o2) {
                    return (int) (o2.getLikes() - o1.getLikes());
                }
            });
        } catch (Exception exception) {

        }

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
            User user = commentForm.getUser();
            Long oldPosts = user.getComments();
            oldPosts = oldPosts == null ? Long.valueOf(0) : oldPosts;
            user.setComments(oldPosts + Long.valueOf(1));
            userService.save(user);
            return new ResponseEntity<>(postEntity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        if (!post.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        post.get().setStatus(0);
        postService.save(post.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{post-id}/post-likes")
    public ResponseEntity<Iterable<PostLike>> getAllLikesByPostId(@PathVariable("post-id") Long postId) {
        Iterable<PostLike> postLike = this.postLikeService.findAllByPostEntityId(postId);
        return new ResponseEntity<>(postLike, HttpStatus.OK);
    }

    @GetMapping("/des/{des}")
    public ResponseEntity<Iterable<Post>> getAllByDes(@PathVariable("des") String des) {
        Iterable<Post> posts = postService.findAllByDescriptionContains(des);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
