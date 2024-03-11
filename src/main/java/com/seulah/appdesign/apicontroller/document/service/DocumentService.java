package com.seulah.appdesign.apicontroller.document.service;

import com.seulah.appdesign.apicontroller.document.dto.LoanResponse;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


@Service
public class DocumentService {
    private final RestTemplate restTemplate;

    public DocumentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    ResponseEntity<LoanResponse> response;

//    public ResponseEntity<?> getLoanDetailsByUser(String userId) {
//        String url = "http://localhost:8091/api/v1/los/loanTypeFormula/getLoanDetailsByUser?userId=" + Long.parseLong(userId);
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("accept", "application/json");
//            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
//            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
//                    LoanResponse.class);
//            firstImage();
//            secondImage();
//            thirdImage();
//            String[] images = {"C:\\test\\test.png", "C:\\test\\test2.png", "C:\\test\\test3.png"};
//            createPdfFromImages(images, "C:\\test\\output.pdf");
//
//            return response; // Or process the response as needed
//        } catch (HttpClientErrorException e) {
//            return ResponseEntity.ok().body("Client error occurred: " + e.getMessage());
//        } catch (ResourceAccessException e) {
//            return ResponseEntity.badRequest().body("Failed to access resource: " + e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
//        }
//    }


    private void firstImage() throws Exception {
        BufferedImage image;
        try (InputStream in = new ClassPathResource("images/1.jpg").getInputStream()) {
            image = ImageIO.read(in);
        }
        // Get graphics context from the image
        Graphics g = image.getGraphics();
        g.setFont(g.getFont().deriveFont(30f)); // Set font size
        g.setColor(Color.BLACK); // Set color for the text
        g.drawString("50555554", 1166, 199); // Draw text
        g.dispose();

        // Save the modified image

        File outputFile = new File("C:\\test\\test.png"); // Ensure this path is accessible
        ImageIO.write(image, "png", outputFile);
    }

    private void secondImage() throws Exception {
        BufferedImage image;
        try (InputStream in = new ClassPathResource("images/2.jpg").getInputStream()) {
            image = ImageIO.read(in);
        }
        // Get graphics context from the image
        Graphics g = image.getGraphics();
        g.setFont(g.getFont().deriveFont(28f)); // Set font size
        g.setColor(Color.BLACK); // Set color for the text
        g.drawString("966505550554", 229, 1812); // Draw text
        g.drawString("Abdullah", 845, 1812);
        g.drawString("1069282455", 845, 1924);
        g.drawString("1/2/2024", 229, 1924);
        g.dispose();

        // Save the modified image

        File outputFile = new File("C:\\test\\test2.png"); // Ensure this path is accessible
        ImageIO.write(image, "png", outputFile);
    }

    private void thirdImage() throws Exception {
        BufferedImage image;
        try (InputStream in = new ClassPathResource("images/3.jpg").getInputStream()) {
            image = ImageIO.read(in);
        }
        // Get graphics context from the image
        Graphics g = image.getGraphics();
        g.setFont(g.getFont().deriveFont(28f)); // Set font size
        g.setColor(Color.BLACK); // Set color for the text
        g.drawString(String.valueOf(response.getBody().getData().getLoanAmount()), 732, 119); // Draw text
        g.drawString(String.valueOf(response.getBody().getData().getMonth()), 195, 423);
        g.drawString(String.valueOf(response.getBody().getData().getInstallmentPerMonth()), 195, 495);
        g.drawString(String.valueOf(response.getBody().getData().getLoanAmount()), 199, 172);
        g.drawString(String.valueOf(response.getBody().getData().getLoanAmount()), 732, 176);
        g.drawString(String.valueOf(response.getBody().getData().getLoanAmount()), 732, 435);
        g.drawString(String.valueOf(response.getBody().getData().getLoanAmount()), 732, 491);
        g.drawString(String.valueOf(""), 732, 491);
        g.dispose();

        // Save the modified image

        File outputFile = new File("C:\\test\\test3.png"); // Ensure this path is accessible
        ImageIO.write(image, "png", outputFile);
    }

    public void createPdfFromImages(String[] imagePaths, String outputPdfPath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            for (String imagePath : imagePaths) {
                System.out.println(imagePath);
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                File imageFile = new File(imagePath); // Use File with the direct path
                try (InputStream in = new FileInputStream(imageFile)) {
                    BufferedImage bimg = ImageIO.read(in);
                    PDImageXObject pdImage;

                    if (imagePath.toLowerCase().endsWith(".png")) {
                        pdImage = JPEGFactory.createFromImage(document, bimg);
                    } else {
                        pdImage = LosslessFactory.createFromImage(document, bimg);
                    }

                    float scale = Math.min(PDRectangle.A4.getWidth() / pdImage.getWidth(), PDRectangle.A4.getHeight() / pdImage.getHeight());
                    float imageWidth = pdImage.getWidth() * scale;
                    float imageHeight = pdImage.getHeight() * scale;
                    float xPosition = (PDRectangle.A4.getWidth() - imageWidth) / 2;
                    float yPosition = (PDRectangle.A4.getHeight() - imageHeight) / 2;

                    try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                        contentStream.drawImage(pdImage, xPosition, yPosition, imageWidth, imageHeight);
                    }
                }
            }
            document.save(outputPdfPath);
        }
    }
}