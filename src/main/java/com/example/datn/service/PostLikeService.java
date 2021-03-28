package com.example.media.service;

import com.example.media.model.UserLikePost;
import com.example.media.model.entity.PostLike;

import java.util.Optional;

public interface PostLikeService {
    void save(PostLike historyEntity);

    void delete(Long id);

    Iterable<PostLike> findAll();

    Optional<PostLike> findById(Long id);

    PostLike findByUserIdAndPostEntityId(Long userId, Long postId);

    Iterable<PostLike> findAllByPostEntityId(Long postId);

    void deleteAllByPostEntityId(Long id);
}
