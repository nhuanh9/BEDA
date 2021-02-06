package com.example.datn.service;

import com.example.datn.model.Image;

public interface ImageService extends IGeneralService<Image> {
    Iterable<Image> findAllByPostId(Long id);
}
