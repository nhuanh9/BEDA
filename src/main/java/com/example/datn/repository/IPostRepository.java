package com.example.datn.repository;

import com.example.datn.model.LinkDoc;
import com.example.datn.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Query("UPDATE Post p SET p.status = 1 WHERE p.id =:id")
    void changStatusPostTrue(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Post p SET p.status = 0 WHERE p.id =:id")
    void changStatusPostFalse(@Param("id") Long id);

    @Modifying
    @Query(value = "select * from Post order by id desc limit 4", nativeQuery = true)
    Iterable<Post> findTop4New();

    Iterable<Post> findByContentContainingAndUserId(String content, Long id);

    Optional<Post> findById(Long id);

    Iterable<Post> findAllByUserId(Long id);

    Iterable<Post> findAllByStatus(int status);

    Iterable<Post> findAllByCategoryId(Long categoryId);

}
