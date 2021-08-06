package com.example.datn.model;

import org.springframework.web.multipart.MultipartFile;

public class Form {
    String name;
    MultipartFile file;

    public Form(String name, MultipartFile file) {
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
