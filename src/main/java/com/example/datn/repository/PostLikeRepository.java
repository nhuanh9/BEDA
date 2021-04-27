package com.example.datn.repository;

import com.example.datn.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByUserIdAndPostEntityId(Long userId, Long postId);

    Iterable<PostLike> findAllByPostEntityId(Long postId);

    void deleteAllByPostEntityId(Long id);


}
