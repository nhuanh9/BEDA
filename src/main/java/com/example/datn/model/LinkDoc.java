package com.example.datn.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class LinkDoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createAt;

    private int status;

    private String link;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Long likes;

    private String description;
    private String linkFile;
    //    @OneToMany(targetEntity = Comment.class)
//    private List<Comment> listComment;
    private String des;
}
