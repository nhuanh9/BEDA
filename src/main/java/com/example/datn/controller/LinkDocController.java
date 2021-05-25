package com.example.datn.controller;

import com.example.datn.model.LinkDoc;
import com.example.datn.model.Post;
import com.example.datn.model.User;
import com.example.datn.service.LinkDocService;
import com.example.datn.service.PostService;
import com.example.datn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

@RestController
@PropertySource("classpath:application.properties")
@CrossOrigin("*")
@RequestMapping("/linkdocs")
public class LinkDocController {

    @Autowired
    LinkDocService linkDocService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Iterable<LinkDoc>> getAll() {
        Iterable<LinkDoc> linkDocs = linkDocService.findAll();
        return new ResponseEntity<>(linkDocs, HttpStatus.OK);
    }


    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Iterable<LinkDoc>> getAllByCategoryName(@PathVariable Long categoryId) {
        Iterable<LinkDoc> linkDocs = linkDocService.findAllByCategoryId(categoryId);
        return new ResponseEntity<>(linkDocs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody LinkDoc linkDoc) {
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        linkDoc.setCreateAt(date);
        linkDoc.setStatus(1);
        linkDoc.setLikes((long) 0);
        linkDocService.save(linkDoc);
        User user = linkDoc.getUser();
        Long oldPosts = user.getLinkDocs();
        oldPosts = oldPosts == null ? Long.valueOf(0) : oldPosts;
        user.setLinkDocs(oldPosts + Long.valueOf(1));
        userService.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/des/{des}")
    public ResponseEntity<Iterable<LinkDoc>> getAllByDes(@PathVariable("des") String des) {
        Iterable<LinkDoc> linkDocs = linkDocService.findAllByDesContains(des);
        return new ResponseEntity<>(linkDocs, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<LinkDoc> linkDoc = linkDocService.findById(id);
        if (!linkDoc.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        linkDoc.get().setStatus(0);
        linkDocService.save(linkDoc.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
