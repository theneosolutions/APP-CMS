package com.seulah.appdesign.apicontroller;


import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@RestController
@RequestMapping("/api/v1/cms")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001","http://localhost:8085"}, maxAge = 3600, allowCredentials = "true")
public class test {

    @GetMapping("/doc")
    public void doc() throws Exception {
        BufferedImage image;
        try (InputStream in = new ClassPathResource("images/2.jpg").getInputStream()) {
            image = ImageIO.read(in);
        }
        // Get graphics context from the image
        Graphics g = image.getGraphics();
        g.setFont(g.getFont().deriveFont(30f)); // Set font size
        g.setColor(Color.BLACK); // Set color for the text
        g.drawString("50555554", 1166, 183); // Draw text
        g.dispose();

        // Save the modified image

        File outputFile = new File("C:\\test\\test.png"); // Ensure this path is accessible
        ImageIO.write(image, "png", outputFile);
    }
}
