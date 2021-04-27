package com.example.datn.service;


import com.example.datn.model.PostLike;

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
