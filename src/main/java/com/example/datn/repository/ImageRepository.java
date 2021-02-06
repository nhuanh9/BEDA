package com.example.datn.repository;

import com.example.datn.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Iterable<Image> findAllByPostId(Long id);
}
