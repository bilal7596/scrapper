package com.example.scrapper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

@Controller
@RequestMapping("/api/image")
public class ImageController {

    private final RestTemplate restTemplate;

    public ImageController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> loadImageFromUrl(@RequestParam String imageUrl, @RequestParam int width, @RequestParam int height) throws IOException {

        BufferedImage originalImage = null;
        if (imageUrl.endsWith("png")) {
            URL imageUrlFormat = new URL(imageUrl);
            originalImage = ImageIO.read(imageUrlFormat);
        } else {
            byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);
            originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
        }

        // Resize the image
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        resizedImage.getGraphics().drawImage(originalImage, 0, 0, width, height, null);

        // Convert the resized image to bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", byteArrayOutputStream);

        // Return the resized image as a byte array
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(byteArrayOutputStream.toByteArray());


//        if (type.equals("png")) {
//            // Make an HTTP GET request to the image URL
//            URL imageUrlFormat = new URL(imageUrl);
//            BufferedImage originalImage = ImageIO.read(imageUrlFormat);
//
//            // Resize the image
//            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            resizedImage.getGraphics().drawImage(originalImage, 0, 0, width, height, null);
//
//            // Convert the resized image to bytes
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ImageIO.write(resizedImage, "jpg", byteArrayOutputStream);
//
//            // Return the resized image as a byte array
//            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(byteArrayOutputStream.toByteArray());
//
////            URL imageUrlFormat = new URL(imageUrl);
////            BufferedImage originalImage = ImageIO.read(imageUrlFormat);
////            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
////            resizedImage.getGraphics().drawImage(originalImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH), 0, 0, null);
////
////            // Convert the resized image to bytes
////            ByteArrayOutputStream baos = new ByteArrayOutputStream();
////            ImageIO.write(resizedImage, "png", baos);
////
////            // Return the resized image as a byte array
////            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(baos.toByteArray());
//        } else {
//            // Make an HTTP GET request to the image URL
//            byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);
//            BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
//
//            // Resize the image
//            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//            resizedImage.getGraphics().drawImage(originalImage, 0, 0, width, height, null);
//
//            // Convert the resized image to bytes
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ImageIO.write(resizedImage, "jpg", byteArrayOutputStream);
//
//            // Return the resized image as a byte array
//            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(byteArrayOutputStream.toByteArray());
//        }



//        byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);
//
//        // Return the image as a byte array
//        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

}
