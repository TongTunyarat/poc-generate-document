package com.example.training.controller;

import com.example.training.entity.Templates;
import com.example.training.service.DocxService;
import com.example.training.service.DownloadService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class DocxController {

    @Autowired
    private DocxService docxService;

    @Autowired
    private DownloadService downloadService;

    @PostMapping("/generate")
    public void generateDocument(
            @RequestBody Map<String, Object> requestBody, HttpServletResponse response
    ) throws IOException {

        String documentType = (String) requestBody.get("documentType");
        Map<String, Object> content = (Map<String, Object>) requestBody.get("content");

        docxService.generateDocument(content, documentType, response);
    }

    @PostMapping("/download")
    public void downloadDocument(
            @RequestBody Map<String, Object> requestBody, HttpServletResponse response
    ) throws IOException {

        String documentType = (String) requestBody.get("documentType");
        Map<String, Object> content = (Map<String, Object>) requestBody.get("content");

        downloadService.generateDocument(content, documentType, response);
    }

    @GetMapping("/templates/{code}")
    public ResponseEntity<Templates> getTemplateByCode(@PathVariable String code) {
        try {
            Templates template = docxService.getTemplateByCode(code);
            return ResponseEntity.ok(template);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}