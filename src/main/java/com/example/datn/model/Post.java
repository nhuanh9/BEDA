package com.example.datn.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date createAt;

    private int status;

    private String content;

    private String detail;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private Long likes;

    @OneToMany(mappedBy = "postId", fetch = FetchType.EAGER)
    private List<Image> imgs = new ArrayList<>();

    @OneToMany(targetEntity = Comment.class)
    private List<Comment> listComment;

    private String description;
}
