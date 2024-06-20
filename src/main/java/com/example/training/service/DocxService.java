package com.example.training.service;

import com.example.training.entity.Templates;
import com.example.training.repository.TemplateRepository;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DocxService {

    @Autowired
    private TemplateRepository templateRepository;

    public void generateDocument(Map<String, Object> data, String documentType, HttpServletResponse response) throws IOException {

        Optional<Templates> optionalTemplate = templateRepository.findByCode(documentType);

        if (optionalTemplate.isEmpty()) {
            System.out.println("Template not found for document type: " + documentType);
            throw new FileNotFoundException("Template not found for document type: " + documentType);
        }

        Templates template = optionalTemplate.get();
        String templatePath = template.getPathFile();

        try (InputStream tp = getClass().getResourceAsStream(templatePath)) {
            if (tp == null) {
                System.out.println("Template file not found in classpath: " + templatePath);
                throw new FileNotFoundException("Template file not found in classpath: " + templatePath);
            }

            XWPFDocument document = new XWPFDocument(tp);
            System.out.println("Template loaded successfully.");

            replacePlaceholder(document, data);

            String docxFilePath = "output.docx";
            saveDocx(document, docxFilePath);

            convertToPdf(docxFilePath, response);

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            throw new IOException("Failed to generate document: " + e.getMessage(), e);
        }
    }

    private void replacePlaceholder(XWPFDocument document, Map<String, Object> data) {
        System.out.println("document: " + document);
        System.out.println("data: " + data);
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            replaceTextInRuns(paragraph.getRuns(), data);
        }

        for (XWPFTable table : document.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        replaceTextInRuns(paragraph.getRuns(), data);
                    }
                }
            }
        }
        System.out.println("Replace data successfully.");
    }

    private void replaceTextInRuns(List<XWPFRun> runs, Map<String, Object> data) {
        for (XWPFRun run : runs) {
            String text = run.getText(0);
            //System.out.println(text);
            if (text != null) {
                text = replaceTextWithMapData(text, data);
                run.setText(text, 0);
            }
        }
    }

    private String replaceTextWithMapData(String text, Map<String, Object> data) {
        //System.out.println(data.entrySet());
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            //System.out.println(entry);
            if (entry.getValue() instanceof Map) {
                text = replaceTextWithMapData(text, (Map<String, Object>) entry.getValue());
            } else if (entry.getValue() != null) {
                text = text.replace("{{" + entry.getKey() + "}}", entry.getValue().toString());
            } else {
                text = text.replace("{{" + entry.getKey() + "}}", "");
            }
        }
        return text;
    }

    private void saveDocx(XWPFDocument document, String filePath) throws IOException {
        try (FileOutputStream out = new FileOutputStream(new File(filePath))) {
            document.write(out);
            System.out.println("Save document successfully.");
        }
    }

    private void convertToPdf(String docxFilePath, HttpServletResponse response) throws IOException {
        Document document = new Document();
        document.loadFromFile(docxFilePath);

        String pdfFilePath = "result.pdf";
        document.saveToFile(pdfFilePath, FileFormat.PDF);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=result.pdf");

        try (InputStream pdfInputStream = new FileInputStream(pdfFilePath);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = pdfInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        new File(pdfFilePath).delete();

        System.out.println("Conversion from DOCX to PDF successfully.");
    }

    public Templates getTemplateByCode(String code) {
        Optional<Templates> templateOptional = templateRepository.findByCode(code);
        return templateOptional.orElseThrow(() -> new RuntimeException("Template not found"));
    }
}