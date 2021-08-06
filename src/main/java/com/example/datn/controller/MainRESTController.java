package com.example.datn.controller;


import com.example.datn.model.Form;
import com.example.datn.model.UploadForm;
import com.example.datn.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin("*")
public class MainRESTController {

    private static String UPLOAD_DIR = "/Users/daonhuanh/Desktop/Codegym/Codegym 09.40.16/Module1/AJAX/image/";

    @PostMapping("/rest/uploadMultiFiles")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadForm form) {
        System.out.println("Description:" + form.getDescription());
        String result = null;
        try {
            result = this.saveUploadedFiles(form.getFiles());
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Uploaded to: <br/>" + result, HttpStatus.OK);

    }

    // Save Files
    private String saveUploadedFiles(MultipartFile[] files) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        uploadDir.mkdirs();
        StringBuilder sb = new StringBuilder();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            String uploadFilePath = UPLOAD_DIR + "/" + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            sb.append(uploadFilePath).append("<br/>");
        }
        return sb.toString();
    }

    @PostMapping("/rest/upload")
    public ResponseEntity<?> multiUploadFileModel2(Form form) {
        User user = new User();
        user.setName(form.getName());
        user.setImageUrls(form.getFile().getOriginalFilename());
        MultipartFile image = form.getFile();
        String fileName = image.getOriginalFilename();
        try {
            FileCopyUtils.copy(image.getBytes(),
                    new File(UPLOAD_DIR + fileName)); // coppy ảnh từ ảnh nhận được vào thư mục quy định,
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);

    }
}