package com.example.datn.service.impl;

import com.example.datn.model.ITUTC;
import com.example.datn.repository.ITUTCRepository;
import com.example.datn.service.ITUTCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ITUTCServiceImpl implements ITUTCService {
    @Autowired
    ITUTCRepository itutcRepository;

    @Override
    public Iterable<ITUTC> findAll() {
        return itutcRepository.findAll();
    }

    @Override
    public ITUTC save(ITUTC itutc) {
        return itutcRepository.save(itutc);
    }

    @Override
    public Optional<ITUTC> findById(Long id) {
        return itutcRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        itutcRepository.deleteById(id);
    }
}
