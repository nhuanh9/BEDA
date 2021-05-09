package com.example.datn.repository;

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
    @Query(value = "select * from Post where status = 1 order by likes desc limit 4 ", nativeQuery = true)
    Iterable<Post> findTop4New();

    Iterable<Post> findByContentContainingAndUserId(String content, Long id);

    Optional<Post> findById(Long id);

    @Modifying
    @Query(value = "select * from Post where status != 0 and user_id = :id", nativeQuery = true)
    Iterable<Post> findAllByUserId(@Param("id") Long id);

    Iterable<Post> findAllByStatus(int status);

    @Modifying
    @Query(value = "select * from Post where status = 1 and category_id = :id", nativeQuery = true)
    Iterable<Post> findAllByCategoryId(@Param("id") Long id);

    @Modifying
    @Query(value = "select * from Post where status != 0 order by likes", nativeQuery = true)
    Iterable<Post> findAllByOrderByLikes();

    Iterable<Post> findAllByDescriptionContains(String des);

    @Modifying
    @Query(value = "select * from Post where status != 0 order by create_at", nativeQuery = true)
    Iterable<Post> findAllOrderByCreateAt();

    @Modifying
    @Query(value = "select * from Post where status = 2 order by id desc", nativeQuery = true)
    Iterable<Post> findAllAdminPost();

}
