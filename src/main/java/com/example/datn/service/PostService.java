package com.example.datn.service;


import com.example.datn.model.Post;

import java.util.Optional;

public interface PostService extends IGeneralService<Post> {
    void changStatusPostTrue(Long id);

    void changStatusPostFalse(Long id);

    Iterable<Post> findAll();

    Iterable<Post> findAllByStatus(int status);

    Iterable<Post> findAllByUserId(Long idUser);

    Iterable<Post> findAllByContent(String content, Long id);

    Optional<Post> findById(Long id);

    Iterable<Post> findAllByCategoryId(Long categoryId);

    Iterable<Post> findTop4Likes();

    Iterable<Post> findAllByDescriptionContains(String des);

    Iterable<Post> findAllOrderByCreateAt();

    Iterable<Post> findAllAdminPost();
}
