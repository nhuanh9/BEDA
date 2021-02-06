package com.example.datn.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Date createAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
