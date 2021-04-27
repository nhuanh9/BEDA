package com.example.datn.controller;

import com.example.datn.model.OrderSeminar;
import com.example.datn.model.Post;
import com.example.datn.service.OrderSeminarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/order-seminars")
public class OrderSeminarController {
    @Autowired
    OrderSeminarService orderSeminarService;

    @PostMapping()
    public ResponseEntity<Post> save(@RequestBody OrderSeminar orderSeminar) {
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        orderSeminar.setCreateAt(date);
        orderSeminarService.save(orderSeminar);
        return new ResponseEntity(orderSeminar, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<OrderSeminar>> getAll() {
        Iterable<OrderSeminar> orderSeminars = orderSeminarService.findAll();
        return new ResponseEntity<>(orderSeminars, HttpStatus.OK);
    }
}
