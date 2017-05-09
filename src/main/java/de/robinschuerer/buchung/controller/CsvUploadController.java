package de.robinschuerer.buchung.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/csv")
@RestController
public class CsvUploadController {

    @PostMapping
    public void handleFileUpload(@RequestParam("file") MultipartFile file) {

        System.out.println(file);
    }

    @GetMapping
    public String test(){
        return "yeah";
    }
}
