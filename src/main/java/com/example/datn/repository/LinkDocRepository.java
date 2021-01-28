package com.example.datn.repository;

import com.example.datn.model.LinkDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkDocRepository extends JpaRepository<LinkDoc, Long> {
    Iterable<LinkDoc> findAllByCategoryName(String categoryName);
    Iterable<LinkDoc> findAllByCategoryId(Long categoryId);
}
