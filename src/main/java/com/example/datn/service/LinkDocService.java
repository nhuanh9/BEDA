package com.example.datn.service;

import com.example.datn.model.LinkDoc;
import com.example.datn.model.Post;
import org.springframework.data.repository.query.Param;

public interface LinkDocService extends IGeneralService<LinkDoc>{
    Iterable<LinkDoc> findAllByCategoryName(String categoryName);
    Iterable<LinkDoc> findAllByCategoryId(Long categoryId);
    Iterable<LinkDoc> findAllByDescriptionContains(String des);
    Iterable<LinkDoc> findAllByUserId(@Param("id") Long id);
    Iterable<LinkDoc> findAllByDesContains(String des);
}
