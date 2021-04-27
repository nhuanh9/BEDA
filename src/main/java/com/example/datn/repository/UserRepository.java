package com.example.datn.repository;


import com.example.datn.model.Post;
import com.example.datn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    @Modifying
    @Query(value = "select * from user_table order by posts desc limit 1", nativeQuery = true)
    Iterable<User> findTopPosts();

    @Modifying
    @Query(value = "select * from user_table order by link_docs desc limit 1", nativeQuery = true)
    Iterable<User> findTopLinkDocs();

    @Modifying
    @Query(value = "select * from user_table order by comments desc limit 1", nativeQuery = true)
    Iterable<User> findTopComments();

}