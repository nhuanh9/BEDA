package com.example.datn.service;

import com.example.datn.model.LinkDoc;
import com.example.datn.model.Post;

public interface LinkDocService extends IGeneralService<LinkDoc>{
    Iterable<LinkDoc> findAllByCategoryName(String categoryName);
    Iterable<LinkDoc> findAllByCategoryId(Long categoryId);
}