package com.shadyplace.springweb;

import com.shadyplace.springweb.models.Image;
import com.shadyplace.springweb.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.GregorianCalendar;

@SpringBootApplication
public class DataCreatorApplication {

    private Logger logger = LoggerFactory.getLogger(DataCreatorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DataCreatorApplication.class);
    }

    @Bean
    public CommandLineRunner dataLoader(ImageService imageService){
        return args -> {
            // Images
            if (imageService.findByLocation("upload/default-image.jpg")==null) {
                logger.info("Creation : image 'default-image'");
                Image image = new Image();
                image.setImageTitle("Shady Place");
                image.setDescription("Shady Place");
                image.setOriginalName("default-image");
                image.setLocation("upload/default-image.jpg");
                image.setMimeType("image/jpeg");
                image.setOriginalName("default-image.jpg");
                image.setAddedAt(new GregorianCalendar());

                imageService.save(image);

                logger.info("image 'default-image' added");
            }



        };

        }



}
