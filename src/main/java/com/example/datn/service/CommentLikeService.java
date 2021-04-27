package com.example.datn.service;

import com.example.datn.model.CommentLike;
import com.example.datn.model.PostLike;

import java.util.Optional;

public interface CommentLikeService {
    void save(CommentLike historyEntity);

    void delete(Long id);

    Iterable<CommentLike> findAll();

    Optional<CommentLike> findById(Long id);

    CommentLike findByUserIdAndCommentId(Long userId, Long commentId);

    Iterable<CommentLike> findAllByCommentId(Long commentId);

    void deleteAllByCommentId(Long id);
}
