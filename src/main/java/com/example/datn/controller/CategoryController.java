package com.example.datn.controller;

import com.example.datn.model.Category;
import com.example.datn.model.Post;
import com.example.datn.model.User;
import com.example.datn.service.ICategoryService;
import com.example.datn.service.UserService;
import com.example.datn.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    ICategoryService categoryService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<Category>> getAll() {
        Iterable<Category> categories = categoryService.findAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        return new ResponseEntity<>(category.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category) {
        categoryService.save(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

}
