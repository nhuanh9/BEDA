package com.example.datn.service.impl;

import com.example.datn.model.LinkDoc;
import com.example.datn.repository.LinkDocRepository;
import com.example.datn.service.LinkDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkDocServiceImpl implements LinkDocService {

    @Autowired
    LinkDocRepository linkDocRepository;

    @Override
    public Iterable<LinkDoc> findAll() {
        return linkDocRepository.findAll();
    }

    @Override
    public LinkDoc save(LinkDoc linkDoc) {
        return linkDocRepository.save(linkDoc);
    }

    @Override
    public Optional<LinkDoc> findById(Long id) {
        return linkDocRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        linkDocRepository.deleteById(id);
    }

    @Override
    public Iterable<LinkDoc> findAllByCategoryName(String categoryName) {
        return linkDocRepository.findAllByCategoryName(categoryName);
    }

    @Override
    public Iterable<LinkDoc> findAllByCategoryId(Long categoryId) {
        return linkDocRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public Iterable<LinkDoc> findAllByDescriptionContains(String des) {
        return linkDocRepository.findAllByDescriptionContains(des);
    }

    @Override
    public Iterable<LinkDoc> findAllByUserId(Long id) {
        return linkDocRepository.findAllByUserId(id);
    }

    @Override
    public Iterable<LinkDoc> findAllByDesContains(String des) {
        return linkDocRepository.findAllByDesContains(des);
    }
}
