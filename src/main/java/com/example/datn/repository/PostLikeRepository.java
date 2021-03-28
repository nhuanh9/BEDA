package com.example.media.repository;

import com.example.media.model.UserLikePost;
import com.example.media.model.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByUserIdAndPostEntityId(Long userId, Long postId);

    Iterable<PostLike> findAllByPostEntityId(Long postId);

    void deleteAllByPostEntityId(Long id);
}
