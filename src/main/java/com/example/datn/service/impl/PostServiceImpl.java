package com.example.datn.service.impl;

import com.example.datn.model.Post;
import com.example.datn.repository.IPostRepository;
import com.example.datn.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    IPostRepository postRepository;

    @Override
    public void changStatusPostTrue(Long id) {
        postRepository.changStatusPostTrue(id);
    }

    @Override
    public void changStatusPostFalse(Long id) {
        postRepository.changStatusPostFalse(id);
    }

    @Override
    public Iterable<Post> findAll() {
        return postRepository.findAllByOrderByLikes();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Iterable<Post> findAllByStatus(int status) {
        return postRepository.findAllByStatus(status);
    }

    @Override
    public Iterable<Post> findAllByUserId(Long idUser) {
        return postRepository.findAllByUserId(idUser);
    }

    @Override
    public Iterable<Post> findAllByContent(String content, Long id) {
        return postRepository.findByContentContainingAndUserId(content, id);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Iterable<Post> findAllByCategoryId(Long categoryId) {
        return postRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public Iterable<Post> findTop4Likes() {
        return postRepository.findTop4New();
    }

    @Override
    public Iterable<Post> findAllByDescriptionContains(String des) {
        return postRepository.findAllByDescriptionContains(des);
    }

    @Override
    public Iterable<Post> findAllOrderByCreateAt() {
        return postRepository.findAllOrderByCreateAt();
    }

    @Override
    public Iterable<Post> findAllAdminPost() {
        return postRepository.findAllAdminPost();
    }

    @Override
    public void remove(Long id) {
        postRepository.deleteById(id);
    }
}
