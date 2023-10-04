package com.shadyplace.springweb;

import com.shadyplace.springweb.models.Equipment;
import com.shadyplace.springweb.models.Image;
import com.shadyplace.springweb.models.enums.EquipmentOption;
import com.shadyplace.springweb.services.EquipmentService;
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
    public CommandLineRunner dataLoader(
            ImageService imageService,
            EquipmentService equipmentService
    ){
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

            // Equipments
            if (equipmentService.findByEquipmentOption(EquipmentOption.A_BED)==null){
                logger.info("Creation : Equipment 'A_BED'");
                Equipment equipment = new Equipment();
                equipment.setOption(EquipmentOption.A_BED);
                equipment.setPrice(9.99d);

                equipmentService.save(equipment);

                logger.info("Equipment 'A_BED' added");
            }
            if (equipmentService.findByEquipmentOption(EquipmentOption.TWO_BEDS)==null){
                logger.info("Creation : Equipment 'TWO_BEDS'");
                Equipment equipment = new Equipment();
                equipment.setOption(EquipmentOption.TWO_BEDS);
                equipment.setPrice(19.98d);

                equipmentService.save(equipment);

                logger.info("Equipment 'TWO_BEDS' added");
            }
            if (equipmentService.findByEquipmentOption(EquipmentOption.A_SEAT)==null){
                logger.info("Creation : Equipment 'A_SEAT'");
                Equipment equipment = new Equipment();
                equipment.setOption(EquipmentOption.A_SEAT);
                equipment.setPrice(4.99d);

                equipmentService.save(equipment);

                logger.info("Equipment 'A_SEAT' added");
            }
            if (equipmentService.findByEquipmentOption(EquipmentOption.A_SEAT_A_BED)==null){
                logger.info("Creation : Equipment 'A_SEAT_A_BED'");
                Equipment equipment = new Equipment();
                equipment.setOption(EquipmentOption.A_SEAT_A_BED);
                equipment.setPrice(14.98d);

                equipmentService.save(equipment);

                logger.info("Equipment 'A_SEAT_A_BED' added");
            }
            if (equipmentService.findByEquipmentOption(EquipmentOption.TWO_SEATS)==null){
                logger.info("Creation : Equipment 'TWO_SEATS'");
                Equipment equipment = new Equipment();
                equipment.setOption(EquipmentOption.TWO_SEATS);
                equipment.setPrice(9.98d);

                equipmentService.save(equipment);

                logger.info("Equipment 'TWO_SEATS' added");
            }
            if (equipmentService.findByEquipmentOption(EquipmentOption.NOTHING)==null){
                logger.info("Creation : Equipment 'NOTHING'");
                Equipment equipment = new Equipment();
                equipment.setOption(EquipmentOption.NOTHING);
                equipment.setPrice(0d);

                equipmentService.save(equipment);

                logger.info("Equipment 'NOTHING' added");
            }

        };

    }



}
