package com.example.datn.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ITUTC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String msv;
}
