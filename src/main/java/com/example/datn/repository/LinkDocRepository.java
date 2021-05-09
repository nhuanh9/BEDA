package com.example.datn.repository;

import com.example.datn.model.LinkDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkDocRepository extends JpaRepository<LinkDoc, Long> {
    Iterable<LinkDoc> findAllByCategoryName(String categoryName);

    Iterable<LinkDoc> findAllByCategoryId(Long categoryId);

    Iterable<LinkDoc> findAllByDescriptionContains(String des);

    @Modifying
    @Query(value = "select * from link_doc where status = 1 and user_id = :id", nativeQuery = true)
    Iterable<LinkDoc> findAllByUserId(@Param("id") Long id);

    @Modifying
    @Query(value = "select * from link_doc where status = 1", nativeQuery = true)
    List<LinkDoc> findAll();

    Iterable<LinkDoc> findAllByDesContains(String des);
}
