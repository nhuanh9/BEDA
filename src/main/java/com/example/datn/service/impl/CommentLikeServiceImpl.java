package com.example.datn.service.impl;

import com.example.datn.model.CommentLike;
import com.example.datn.repository.CommentLikeRepository;
import com.example.datn.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {
    @Autowired
    CommentLikeRepository commentLikeRepository;

    @Override
    public void save(CommentLike historyEntity) {
        commentLikeRepository.save(historyEntity);
    }

    @Override
    public void delete(Long id) {
        commentLikeRepository.deleteById(id);
    }

    @Override
    public Iterable<CommentLike> findAll() {
        return commentLikeRepository.findAll();
    }

    @Override
    public Optional<CommentLike> findById(Long id) {
        return commentLikeRepository.findById(id);
    }

    @Override
    public CommentLike findByUserIdAndCommentId(Long userId, Long commentId) {
        return commentLikeRepository.findByUserIdAndCommentId(userId, commentId);
    }

    @Override
    public Iterable<CommentLike> findAllByCommentId(Long commentId) {
        return commentLikeRepository.findAllByCommentId(commentId);
    }

    @Override
    public void deleteAllByCommentId(Long id) {
        commentLikeRepository.deleteAllByCommentId(id);
    }
}
