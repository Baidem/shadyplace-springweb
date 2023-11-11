package com.shadyplace.springweb;

import com.shadyplace.springweb.models.articleBlog.Image;
import com.shadyplace.springweb.models.bookingResa.Equipment;
import com.shadyplace.springweb.models.bookingResa.Line;
import com.shadyplace.springweb.models.enums.Country;
import com.shadyplace.springweb.models.enums.FamilyLinkLabel;
import com.shadyplace.springweb.models.userAuth.FamilyLink;
import com.shadyplace.springweb.models.userAuth.FidelityRank;
import com.shadyplace.springweb.models.userAuth.Role;
import com.shadyplace.springweb.models.userAuth.User;
import com.shadyplace.springweb.services.articleBlog.ImageService;
import com.shadyplace.springweb.services.bookingResa.EquipmentService;
import com.shadyplace.springweb.services.bookingResa.LineService;
import com.shadyplace.springweb.services.userAuth.FamilyLinkService;
import com.shadyplace.springweb.services.userAuth.FidelityRankService;
import com.shadyplace.springweb.services.userAuth.RoleService;
import com.shadyplace.springweb.services.userAuth.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;
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
            EquipmentService equipmentService,
            FamilyLinkService familyLinkService,
            FidelityRankService fidelityRankService,
            UserService userService,
            LineService lineService,
            RoleService roleService
    ){
        return args -> {
// Image par defaut
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

// Roles
            if (roleService.findByRoleName("ADMIN")==null) {
                logger.info("Creation : Role 'ADMIN'");
                Role role = new Role();
                role.setRoleName("ADMIN");

                roleService.save(role);

                logger.info("role 'ADMIN' added");
            }
            if (roleService.findByRoleName("USER")==null) {
                logger.info("Creation : Role 'USER'");
                Role role = new Role();
                role.setRoleName("USER");

                roleService.save(role);

                logger.info("role 'USER' added");
            }

// Family links
            if (familyLinkService.findByLabel(FamilyLinkLabel.PARENT)==null) {
                logger.info("Creation : FamilyLink 'PARENT'");
                FamilyLink familyLink = new FamilyLink();
                familyLink.setLabel(FamilyLinkLabel.PARENT);
                familyLink.setDiscountRate(0.9d);

                familyLinkService.save(familyLink);

                logger.info("FamilyLink 'PARENT' added");
            }
            if (familyLinkService.findByLabel(FamilyLinkLabel.GRANDPARENT)==null) {
                logger.info("Creation : FamilyLink 'GRANDPARENT'");
                FamilyLink familyLink = new FamilyLink();
                familyLink.setLabel(FamilyLinkLabel.GRANDPARENT);
                familyLink.setDiscountRate(0.8d);

                familyLinkService.save(familyLink);

                logger.info("FamilyLink 'GRANDPARENT' added");
            }
            if (familyLinkService.findByLabel(FamilyLinkLabel.CHILD)==null) {
                logger.info("Creation : FamilyLink 'CHILD'");
                FamilyLink familyLink = new FamilyLink();
                familyLink.setLabel(FamilyLinkLabel.CHILD);
                familyLink.setDiscountRate(0.7d);

                familyLinkService.save(familyLink);

                logger.info("FamilyLink 'CHILD' added");
            }
            if (familyLinkService.findByLabel(FamilyLinkLabel.SIBLING)==null) {
                logger.info("Creation : FamilyLink 'SIBLING'");
                FamilyLink familyLink = new FamilyLink();
                familyLink.setLabel(FamilyLinkLabel.SIBLING);
                familyLink.setDiscountRate(0.5d);

                familyLinkService.save(familyLink);

                logger.info("FamilyLink 'SIBLING' added");
            }
            if (familyLinkService.findByLabel(FamilyLinkLabel.UNCLE_AUNT)==null) {
                logger.info("Creation : FamilyLink 'UNCLE_AUNT'");
                FamilyLink familyLink = new FamilyLink();
                familyLink.setLabel(FamilyLinkLabel.UNCLE_AUNT);
                familyLink.setDiscountRate(0.3d);

                familyLinkService.save(familyLink);

                logger.info("FamilyLink 'UNCLE_AUNT' added");
            }
            if (familyLinkService.findByLabel(FamilyLinkLabel.COUSIN)==null) {
                logger.info("Creation : FamilyLink 'COUSIN'");
                FamilyLink familyLink = new FamilyLink();
                familyLink.setLabel(FamilyLinkLabel.COUSIN);
                familyLink.setDiscountRate(0.25d);

                familyLinkService.save(familyLink);

                logger.info("FamilyLink 'COUSIN' added");
            }
            if (familyLinkService.findByLabel(FamilyLinkLabel.NO_FAMILY)==null) {
                logger.info("Creation : FamilyLink 'NO_FAMILY'");
                FamilyLink familyLink = new FamilyLink();
                familyLink.setLabel(FamilyLinkLabel.NO_FAMILY);
                familyLink.setDiscountRate(0d);

                familyLinkService.save(familyLink);

                logger.info("FamilyLink 'NO_FAMILY' added");
            }

// Fidelity ranks
            if (fidelityRankService.findByLabel("Newcomer") == null) {
                logger.info("Creation : FidelityRank 'Newcomer'");
                FidelityRank f = new FidelityRank();
                f.setLabel("Newcomer");
                f.setDiscountPrice(0);

                fidelityRankService.save(f);

                logger.info("Fidelity rank 'Newcomer' added");
            }
            if (fidelityRankService.findByLabel("Rookie") == null) {
                logger.info("Creation : FidelityRank 'Rookie'");
                FidelityRank fidelityRank = new FidelityRank();
                fidelityRank.setLabel("Rookie");
                fidelityRank.setDiscountPrice(1.25); // Réduction de 1.25 pour les Rookies

                fidelityRankService.save(fidelityRank);

                logger.info("Fidelity rank 'Rookie' added");
            }
            if (fidelityRankService.findByLabel("Member") == null) {
                logger.info("Creation : FidelityRank 'Member'");
                FidelityRank fidelityRank = new FidelityRank();
                fidelityRank.setLabel("Member");
                fidelityRank.setDiscountPrice(2.5); // Réduction de 2.5 pour les Members

                fidelityRankService.save(fidelityRank);

                logger.info("Fidelity rank 'Member' added");
            }
            if (fidelityRankService.findByLabel("Premium") == null) {
                logger.info("Creation : FidelityRank 'Premium'");
                FidelityRank fidelityRank = new FidelityRank();
                fidelityRank.setLabel("Premium");
                fidelityRank.setDiscountPrice(3.75); // Réduction de 3.75 pour les Premium

                fidelityRankService.save(fidelityRank);

                logger.info("Fidelity rank 'Premium' added");
            }
            if (fidelityRankService.findByLabel("VIP") == null) {
                logger.info("Creation : FidelityRank 'VIP'");
                FidelityRank fidelityRank = new FidelityRank();
                fidelityRank.setLabel("VIP");
                fidelityRank.setDiscountPrice(5); // Réduction de 5 pour les VIP

                fidelityRankService.save(fidelityRank);

                logger.info("Fidelity rank 'VIP' added");
            }

// Equipments
            if (equipmentService.findByOption("1 bed")==null){
                logger.info("Creation : Equipment '1 bed'");
                Equipment equipment = new Equipment();
                equipment.setOption("1 bed");
                equipment.setPrice(9.99);

                equipmentService.save(equipment);

                logger.info("Equipment '1 bed' added");
            }
            if (equipmentService.findByOption("2 beds")==null){
                logger.info("Creation : Equipment '2 beds'");
                Equipment equipment = new Equipment();
                equipment.setOption("2 beds");
                equipment.setPrice(19.98);

                equipmentService.save(equipment);

                logger.info("Equipment '2 beds' added");
            }
            if (equipmentService.findByOption("1 seat")==null){
                logger.info("Creation : Equipment '1 seat'");
                Equipment equipment = new Equipment();
                equipment.setOption("1 seat");
                equipment.setPrice(4.99);

                equipmentService.save(equipment);

                logger.info("Equipment '1 seat' added");
            }
            if (equipmentService.findByOption("1 bed + 1 seat")==null){
                logger.info("Creation : Equipment '1 bed + 1 seat'");
                Equipment equipment = new Equipment();
                equipment.setOption("1 bed + 1 seat");
                equipment.setPrice(14.98);

                equipmentService.save(equipment);

                logger.info("Equipment '1 bed + 1 seat' added");
            }
            if (equipmentService.findByOption("2 seats")==null){
                logger.info("Creation : Equipment '2 seats'");
                Equipment equipment = new Equipment();
                equipment.setOption("2 seats");
                equipment.setPrice(9.98);

                equipmentService.save(equipment);

                logger.info("Equipment '2 seats' added");
            }
            if (equipmentService.findByOption("nothing")==null){
                logger.info("Creation : Equipment 'nothing'");
                Equipment equipment = new Equipment();
                equipment.setOption("nothing");
                equipment.setPrice(0);

                equipmentService.save(equipment);

                logger.info("Equipment 'nothing' added");
            }
// lines
            if (lineService.findLineByLabel("first line facing the sea")==null){
                logger.info("Creation : Line 'first line facing the sea'");
                Line l = new Line();
                l.setLabel("first line facing the sea");
                l.setPrice(35.99);

                lineService.save(l);

                logger.info("Line 'first line facing the sea' added");
            }
            if (lineService.findLineByLabel("second line facing the sea")==null){
                logger.info("Creation : Line 'second line facing the sea'");
                Line l = new Line();
                l.setLabel("second line facing the sea");
                l.setPrice(32.99);

                lineService.save(l);

                logger.info("Line 'second line facing the sea' added");
            }
            if (lineService.findLineByLabel("other line")==null){
                logger.info("Creation : Line 'other line'");
                Line l = new Line();
                l.setLabel("other line");
                l.setPrice(29.99);

                lineService.save(l);

                logger.info("Line 'other line");
            }

// Users
            if (userService.findByEmail("baidem@gmail.com")==null){
                logger.info("Creation : User 'Vincent'");
                User user = new User();
                user.setEmail("baidem@gmail.com");
                user.setFirstname("Vincent");
                user.setLastname("Poupaert");
                user.setPassword("$2y$10$LXglFW2ICkJ6pSkqgjG/2eDCStEfrwFWNJlbX3963Rfs2sunxuqHq"); // MP : poulet
                user.setConfirmPassword("$2y$10$LXglFW2ICkJ6pSkqgjG/2eDCStEfrwFWNJlbX3963Rfs2sunxuqHq");
                user.setResidenceCountry(Country.FRANCE);
                user.setRegistrationDate(Calendar.getInstance());
                user.setFamilyLink(familyLinkService.findByLabel(FamilyLinkLabel.NO_FAMILY));
                user.setCurrentFidelityRank(fidelityRankService.findByLabel("VIP"));
                user.addRole(roleService.findByRoleName("ADMIN"));

                userService.save(user);

                logger.info("User 'Vincent' added");
            }
            String HashOfSecret456 = "$2a$10$xALSaBcbaNbhvm6x3BPm7OCQCvBnAdtWyQ94TdZvK4j0vZO3Myo8y"; // MP : secret456
// User 1
            if (userService.findByEmail("alice@example.com") == null) {
                logger.info("Creation : User 'Alice'");
                User user = new User();
                user.setEmail("alice@example.com");
                user.setFirstname("Alice");
                user.setLastname("Johnson");
                user.setPassword(HashOfSecret456); // MP : secret456
                user.setConfirmPassword(HashOfSecret456); // MP : secret456
                user.setResidenceCountry(Country.UNITED_STATES);
                user.setRegistrationDate(Calendar.getInstance());
                user.setFamilyLink(familyLinkService.findByLabel(FamilyLinkLabel.NO_FAMILY));
                user.setCurrentFidelityRank(fidelityRankService.findByLabel("Member"));
                user.addRole(roleService.findByRoleName("USER"));

                userService.save(user);

                logger.info("User 'Alice' added");
            }
// User 2
            if (userService.findByEmail("bob@example.com") == null) {
                logger.info("Creation : User 'Bob'");
                User user = new User();
                user.setEmail("bob@example.com");
                user.setFirstname("Bob");
                user.setLastname("Smith");
                user.setPassword(HashOfSecret456); // MP : secret456
                user.setConfirmPassword(HashOfSecret456); // MP : secret456
                user.setResidenceCountry(Country.CANADA);
                user.setRegistrationDate(Calendar.getInstance());
                user.setFamilyLink(familyLinkService.findByLabel(FamilyLinkLabel.CHILD));
                user.setCurrentFidelityRank(fidelityRankService.findByLabel("Rookie"));
                user.addRole(roleService.findByRoleName("USER"));

                userService.save(user);

                logger.info("User 'Bob' added");
            }
// User 3
            if (userService.findByEmail("charlie@example.com") == null) {
                logger.info("Creation : User 'Charlie'");
                User user = new User();
                user.setEmail("charlie@example.com");
                user.setFirstname("Charlie");
                user.setLastname("Brown");
                user.setPassword(HashOfSecret456); // MP : secret456
                user.setConfirmPassword(HashOfSecret456); // MP : secret456
                user.setResidenceCountry(Country.FRANCE);
                user.setRegistrationDate(Calendar.getInstance());
                user.setFamilyLink(familyLinkService.findByLabel(FamilyLinkLabel.GRANDPARENT));
                user.setCurrentFidelityRank(fidelityRankService.findByLabel("Premium"));
                user.addRole(roleService.findByRoleName("USER"));

                userService.save(user);

                logger.info("User 'Charlie' added");
            }
// User 4
            if (userService.findByEmail("david@example.com") == null) {
                logger.info("Creation : User 'David'");
                User user = new User();
                user.setEmail("david@example.com");
                user.setFirstname("David");
                user.setLastname("Wilson");
                user.setPassword(HashOfSecret456); // MP : secret456
                user.setConfirmPassword(HashOfSecret456); // MP : secret456
                user.setResidenceCountry(Country.UNITED_KINGDOM);
                user.setRegistrationDate(Calendar.getInstance());
                user.setFamilyLink(familyLinkService.findByLabel(FamilyLinkLabel.PARENT));
                user.setCurrentFidelityRank(fidelityRankService.findByLabel("Newcomer"));
                user.addRole(roleService.findByRoleName("USER"));

                userService.save(user);

                logger.info("User 'David' added");
            }
// User 5
            if (userService.findByEmail("emily@example.com") == null) {
                logger.info("Creation : User 'Emily'");
                User user = new User();
                user.setEmail("emily@example.com");
                user.setFirstname("Emily");
                user.setLastname("Davis");
                user.setPassword(HashOfSecret456); // MP : secret456
                user.setConfirmPassword(HashOfSecret456); // MP : secret456
                user.setResidenceCountry(Country.AUSTRALIA);
                user.setRegistrationDate(Calendar.getInstance());
                user.setFamilyLink(familyLinkService.findByLabel(FamilyLinkLabel.NO_FAMILY));
                user.setCurrentFidelityRank(fidelityRankService.findByLabel("Member"));
                user.addRole(roleService.findByRoleName("USER"));

                userService.save(user);

                logger.info("User 'Emily' added");
            }
// User 6
            if (userService.findByEmail("frank@example.com") == null) {
                logger.info("Creation : User 'Frank'");
                User user = new User();
                user.setEmail("frank@example.com");
                user.setFirstname("Frank");
                user.setLastname("Johnson");
                user.setPassword(HashOfSecret456); // MP : secret456
                user.setConfirmPassword(HashOfSecret456); // MP : secret456
                user.setResidenceCountry(Country.GERMANY);
                user.setRegistrationDate(Calendar.getInstance());
                user.setFamilyLink(familyLinkService.findByLabel(FamilyLinkLabel.CHILD));
                user.setCurrentFidelityRank(fidelityRankService.findByLabel("Rookie"));
                user.addRole(roleService.findByRoleName("USER"));

                userService.save(user);

                logger.info("User 'Frank' added");
            }
// User 7
            if (userService.findByEmail("sophie@example.com") == null) {
                logger.info("Creation : User 'Sophie'");
                User user = new User();
                user.setEmail("sophie@example.com");
                user.setFirstname("Sophie");
                user.setLastname("Martinez");
                user.setPassword(HashOfSecret456); // MP : secret456
                user.setConfirmPassword(HashOfSecret456); // MP : secret456
                user.setResidenceCountry(Country.UNITED_STATES);
                user.setRegistrationDate(Calendar.getInstance());
                user.setFamilyLink(familyLinkService.findByLabel(FamilyLinkLabel.GRANDPARENT));
                user.setCurrentFidelityRank(fidelityRankService.findByLabel("Member"));
                user.addRole(roleService.findByRoleName("USER"));

                userService.save(user);

                logger.info("User 'Sophie' added");
            }
// User 8
            if (userService.findByEmail("james@example.com") == null) {
                logger.info("Creation : User 'James'");
                User user = new User();
                user.setEmail("james@example.com");
                user.setFirstname("James");
                user.setLastname("Smith");
                user.setPassword(HashOfSecret456); // MP : secret456
                user.setConfirmPassword(HashOfSecret456); // MP : secret456
                user.setResidenceCountry(Country.CANADA);
                user.setRegistrationDate(Calendar.getInstance());
                user.setFamilyLink(familyLinkService.findByLabel(FamilyLinkLabel.SIBLING));
                user.setCurrentFidelityRank(fidelityRankService.findByLabel("Rookie"));
                user.addRole(roleService.findByRoleName("USER"));

                userService.save(user);

                logger.info("User 'James' added");
            }
// User 9
            if (userService.findByEmail("linda@example.com") == null) {
                logger.info("Creation : User 'Linda'");
                User user = new User();
                user.setEmail("linda@example.com");
                user.setFirstname("Linda");
                user.setLastname("Brown");
                user.setPassword(HashOfSecret456); // MP : secret456
                user.setConfirmPassword(HashOfSecret456); // MP : secret456
                user.setResidenceCountry(Country.UNITED_STATES);
                user.setRegistrationDate(Calendar.getInstance());
                user.setFamilyLink(familyLinkService.findByLabel(FamilyLinkLabel.CHILD));
                user.setCurrentFidelityRank(fidelityRankService.findByLabel("Member"));
                user.addRole(roleService.findByRoleName("USER"));

                userService.save(user);

                logger.info("User 'Linda' added");
            }
// User 10
            if (userService.findByEmail("michael@example.com") == null) {
                logger.info("Creation : User 'Michael'");
                User user = new User();
                user.setEmail("michael@example.com");
                user.setFirstname("Michael");
                user.setLastname("Wilson");
                user.setPassword(HashOfSecret456); // MP : secret456
                user.setConfirmPassword(HashOfSecret456); // MP : secret456
                user.setResidenceCountry(Country.AUSTRALIA);
                user.setRegistrationDate(Calendar.getInstance());
                user.setFamilyLink(familyLinkService.findByLabel(FamilyLinkLabel.NO_FAMILY));
                user.setCurrentFidelityRank(fidelityRankService.findByLabel("Premium"));
                user.addRole(roleService.findByRoleName("USER"));

                userService.save(user);

                logger.info("User 'Michael' added");
            }
        };
    }
}
