package com.example.datn.controller;

import com.example.datn.model.Category;
import com.example.datn.model.Image;
import com.example.datn.model.Post;
import com.example.datn.service.ImageService;
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
@RequestMapping("/images")
public class ImageController {
    @Autowired
    ImageService imageService;

    @GetMapping
    public ResponseEntity<Iterable<Image>> getAll() {
        Iterable<Image> images = imageService.findAll();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/{post-id}")
    public ResponseEntity<Iterable<Image>> getAll(@PathVariable(name = "post-id") Long postId) {
        Iterable<Image> images = imageService.findAllByPostId(postId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Image> save(@RequestBody Image image) {
        imageService.save(image);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
