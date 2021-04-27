package com.example.datn.controller;

import com.example.datn.model.*;
import com.example.datn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/comment-likes")
public class CommentLikeController {
    @Autowired
    PostLikeService postLikeService;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    CommentLikeService commentLikeService;

    @Autowired
    CommentService commentService;


    private boolean checkLike(User user, Comment comment, Iterable<CommentLike> commentLikes) {
        for (CommentLike i : commentLikes) {
            if (i.getComment() == comment && i.getUser() == user && i.isLiked()) {
                return false;
            }
        }
        return true;
    }

    @GetMapping
    public ResponseEntity<Iterable<CommentLike>> getAll() {
        Iterable<CommentLike> commentLikes = commentLikeService.findAll();
        return new ResponseEntity<>(commentLikes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CommentLike>> getById(@PathVariable Long id) {
        Optional<CommentLike> commentLike = this.commentLikeService.findById(id);
        if (!commentLike.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentLike, HttpStatus.OK);
    }

    @GetMapping("/{user-id}/{comment-id}")
    public ResponseEntity<CommentLike> getByUserIdAndPostId(@PathVariable("user-id") Long userId, @PathVariable("comment-id") Long commentId) {
        CommentLike commentLike = this.commentLikeService.findByUserIdAndCommentId(userId, commentId);
        return new ResponseEntity<>(commentLike, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<CommentLike> commentLike = this.commentLikeService.findById(id);
        if (!commentLike.isPresent()) {
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
    public ResponseEntity edit(@RequestBody CommentLike commentLike, @PathVariable Long id) {
        Optional<CommentLike> commentLike1 = this.commentLikeService.findById(id);
        if (!commentLike1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentLike.setId(commentLike1.get().getId());
        commentLikeService.save(commentLike);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity like(@RequestBody CommentLike like) {
        Comment comment = commentService.findById(like.getComment().getId()).get();
        User user = userService.findById(like.getUser().getId()).get();
        CommentLike lastLike = commentLikeService.findByUserIdAndCommentId(user.getId(), comment.getId());
        if (checkLike(user, comment, commentLikeService.findAll())) {
            if (lastLike == null) {
                like.setComment(comment);
                like.setLiked(true);
                commentLikeService.save(like);
            } else {
                lastLike.setLiked(true);
                commentLikeService.save(lastLike);
            }
            Long oldLikes = comment.getLikes();
            oldLikes = oldLikes == null ? Long.valueOf(0) : oldLikes;
            comment.setLikes(oldLikes + Long.valueOf(1));
            commentService.save(comment);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/unlike")
    public ResponseEntity unlike(@RequestBody CommentLike like) {
        Comment comment = commentService.findById(like.getComment().getId()).get();
        User user = userService.findById(like.getUser().getId()).get();
        CommentLike lastLike = commentLikeService.findByUserIdAndCommentId(user.getId(), comment.getId());
        if (!checkLike(user, comment, commentLikeService.findAll())) {
            lastLike.setLiked(false);
            Long oldLikes = comment.getLikes();
            oldLikes = oldLikes == null ? Long.valueOf(0) : oldLikes;
            comment.setLikes(oldLikes - Long.valueOf(1));
            commentService.save(comment);
            commentLikeService.save(lastLike);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
