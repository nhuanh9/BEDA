package com.example.datn.service.impl;

import com.example.datn.model.OrderSeminar;
import com.example.datn.repository.OrderSeminarRepository;
import com.example.datn.service.OrderSeminarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderSeminarServiceImpl implements OrderSeminarService {
    @Autowired
    OrderSeminarRepository orderSeminarRepository;

    @Override
    public Iterable<OrderSeminar> findAll() {
        return orderSeminarRepository.findAll();
    }

    @Override
    public OrderSeminar save(OrderSeminar orderSeminar) {
        return orderSeminarRepository.save(orderSeminar);
    }

    @Override
    public Optional<OrderSeminar> findById(Long id) {
        return orderSeminarRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        orderSeminarRepository.deleteById(id);
    }
}
