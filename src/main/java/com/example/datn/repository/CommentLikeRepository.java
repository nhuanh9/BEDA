package com.example.datn.repository;

import com.example.datn.model.CommentLike;
import com.example.datn.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findByUserIdAndCommentId(Long userId, Long commentId);

    Iterable<CommentLike> findAllByCommentId(Long commentId);

    void deleteAllByCommentId(Long id);

}