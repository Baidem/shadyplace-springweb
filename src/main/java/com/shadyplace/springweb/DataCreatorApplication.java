package com.shadyplace.springweb;

import com.shadyplace.springweb.models.*;
import com.shadyplace.springweb.models.enums.Country;
import com.shadyplace.springweb.models.enums.EquipmentOption;
import com.shadyplace.springweb.models.enums.FamilyLinkLabel;
import com.shadyplace.springweb.services.*;
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
// Users
            if (userService.findByEmail("baidem@gmail.com")==null){
                logger.info("Creation : User 'Vincent'");
                User user = new User();
                user.setEmail("baidem@gmail.com");
                user.setFirstname("Vincent");
                user.setLastname("Poupaert");
                user.setPassword("$2y$10$LXglFW2ICkJ6pSkqgjG/2eDCStEfrwFWNJlbX3963Rfs2sunxuqHq");
                user.setConfirmPassword("$2y$10$LXglFW2ICkJ6pSkqgjG/2eDCStEfrwFWNJlbX3963Rfs2sunxuqHq");
                user.setResidenceCountry(Country.FRANCE);
                user.setRegistrationDate(Calendar.getInstance());
                user.setFamilyLink(familyLinkService.findByLabel(FamilyLinkLabel.NO_FAMILY));
                user.setCurrentFidelityRank(fidelityRankService.findByLabel("VIP"));
                user.addRole(roleService.findByRoleName("ADMIN"));

                userService.save(user);

                logger.info("User 'Vincent' added");
            }
// User 1
            if (userService.findByEmail("alice@example.com") == null) {
                logger.info("Creation : User 'Alice'");
                User user = new User();
                user.setEmail("alice@example.com");
                user.setFirstname("Alice");
                user.setLastname("Johnson");
                user.setPassword("$2a$10$K28OexlX03x2zE1N9L5pBeZ6Lv9EaF2MvB9iqNtMpmzNoD4aXqtSu"); // Mot de passe : secret123
                user.setConfirmPassword("$2a$10$K28OexlX03x2zE1N9L5pBeZ6Lv9EaF2MvB9iqNtMpmzNoD4aXqtSu"); // Mot de passe : secret123
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
                user.setPassword("$2a$10$gqI6XExVr9xxafPbwMYf2O0rkwCPxvVXHz/zlj77.Pp68LLEdZcSy"); // Mot de passe : secure456
                user.setConfirmPassword("$2a$10$gqI6XExVr9xxafPbwMYf2O0rkwCPxvVXHz/zlj77.Pp68LLEdZcSy"); // Mot de passe : secure456
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
                user.setPassword("$2a$10$D7G4KfIK2er6g3b9/HTyP.DuYsM.kAHzqDeFSeIjjSkqssJFYqK76"); // Mot de passe : mypass789
                user.setConfirmPassword("$2a$10$D7G4KfIK2er6g3b9/HTyP.DuYsM.kAHzqDeFSeIjjSkqssJFYqK76"); // Mot de passe : mypass789
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
                user.setPassword("$2a$10$j2zFy4Syj21y18QsyVlMU.FOQy3AsBksB/s89o/OjFXGmX4uy7LeK"); // Mot de passe : secure789
                user.setConfirmPassword("$2a$10$j2zFy4Syj21y18QsyVlMU.FOQy3AsBksB/s89o/OjFXGmX4uy7LeK"); // Mot de passe : secure789
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
                user.setPassword("$2a$10$9zSyBzT63Zy1yB6gkhoVceZOjDP9Qw1p2V8y7uz63SgK9nxy7nxjO"); // Mot de passe : secret456
                user.setConfirmPassword("$2a$10$9zSyBzT63Zy1yB6gkhoVceZOjDP9Qw1p2V8y7uz63SgK9nxy7nxjO"); // Mot de passe : secret456
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
                user.setPassword("$2a$10$f.5Jld9MRNn81Nq9nl5BQMOODvrrUyXq4.qZQ.7e1wa.xRZFR1Qm3"); // Mot de passe : mypass123
                user.setConfirmPassword("$2a$10$f.5Jld9MRNn81Nq9nl5BQMOODvrrUyXq4.qZQ.7e1wa.xRZFR1Qm3"); // Mot de passe : mypass123
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
                user.setPassword("$2a$10$WUvXNfbKqnql1R.jTRf2Uud5ncf/LBGJqD/0p2cY3z4ON/GpZNRQm"); // Mot de passe : secure123
                user.setConfirmPassword("$2a$10$WUvXNfbKqnql1R.jTRf2Uud5ncf/LBGJqD/0p2cY3z4ON/GpZNRQm"); // Mot de passe : secure123
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
                user.setPassword("$2a$10$rIdRoZLZ1ZyJZ7s91EDBhuBmVQ8gfiO.zmr..g0Fz70j1mQYYN/EE"); // Mot de passe : mysecret456
                user.setConfirmPassword("$2a$10$rIdRoZLZ1ZyJZ7s91EDBhuBmVQ8gfiO.zmr..g0Fz70j1mQYYN/EE"); // Mot de passe : mysecret456
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
                user.setPassword("$2a$10$j1zP/BrOYfz8I7/mqKKHZOYvGokk0waV/T2WlHvf0RRi.t5QpZl9O"); // Mot de passe : secret789
                user.setConfirmPassword("$2a$10$j1zP/BrOYfz8I7/mqKKHZOYvGokk0waV/T2WlHvf0RRi.t5QpZl9O"); // Mot de passe : secret789
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
                user.setPassword("$2a$10$kDxqK/CVDfRBXhH.JIh3VeS3K/tE5b4xlyw2C9YImfgM6hPXxt9mO"); // Mot de passe : secure234
                user.setConfirmPassword("$2a$10$kDxqK/CVDfRBXhH.JIh3VeS3K/tE5b4xlyw2C9YImfgM6hPXxt9mO"); // Mot de passe : secure234
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
