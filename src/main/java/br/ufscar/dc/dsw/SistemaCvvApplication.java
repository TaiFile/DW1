package br.ufscar.dc.dsw;

import br.ufscar.dc.dsw.dao.*;
import br.ufscar.dc.dsw.domain.*;
import br.ufscar.dc.dsw.domain.enums.OfferStatus;
import br.ufscar.dc.dsw.domain.enums.SexEnum;
import br.ufscar.dc.dsw.domain.enums.UserRoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableAsync
public class SistemaCvvApplication {

    private static final Logger logger = LoggerFactory.getLogger(SistemaCvvApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SistemaCvvApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner seed(IUserDAO userDAO, IClientDAO clientDAO, IStoreDAO storeDAO,
                                  IVehicleDAO vehicleDAO, IOfferDAO offerDAO,
                                  BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            try {
                // Verificar se já existem dados antes de inserir
                if (userDAO.count() > 0) {
                    logger.warn("Database already contains data, skipping seed");
                    return;
                }

                // Limpar dados existentes na ordem correta (respeitando foreign keys)
                logger.info("Cleaning existing data...");
                offerDAO.deleteAll();
                vehicleDAO.deleteAll();
                userDAO.deleteAll();
                clientDAO.deleteAll();
                storeDAO.deleteAll();
                logger.info("Existing data cleaned successfully");

                // Criar Admin
                logger.info("Creating admin...");
                User user = new User();
                user.setEmail("admin@example.com");
                user.setPassword(passwordEncoder.encode("123"));
                user.setRole(UserRoleEnum.ADMIN);
                user.setEnabled(true);
                user = userDAO.save(user);
                logger.info("Admin created with ID: {}", user.getId());

                // Criar Clientes
                logger.info("Creating clients...");
                Client client1 = new Client();
                client1.setEmail("client1@example.com");
                client1.setPassword(passwordEncoder.encode("123"));
                client1.setRole(UserRoleEnum.CLIENT);
                client1.setEnabled(true);
                client1.setCpf("123.456.789-01");
                client1.setName("Carlos Eduardo");
                client1.setPhone("11997784512");
                client1.setSex(SexEnum.MALE);
                client1.setDateOfBirth(LocalDate.of(2000, 1, 8));
                client1 = clientDAO.save(client1);
                logger.info("Client1 created with ID: {}", client1.getId());

                Client client2 = new Client();
                client2.setEmail("client2@example.com");
                client2.setPassword(passwordEncoder.encode("123"));
                client2.setRole(UserRoleEnum.CLIENT);
                client2.setEnabled(true);
                client2.setCpf("021.234.567-89");
                client2.setName("Amanda");
                client2.setPhone("11997123654");
                client2.setSex(SexEnum.FEMALE);
                client2.setDateOfBirth(LocalDate.of(2000, 7, 12));
                client2 = clientDAO.save(client2);
                logger.info("Client2 created with ID: {}", client2.getId());

                // Criar Lojas
                logger.info("Creating stores...");
                Store store1 = new Store();
                store1.setEmail("store1@example.com");
                store1.setPassword(passwordEncoder.encode("123"));
                store1.setRole(UserRoleEnum.STORE);
                store1.setEnabled(true);
                store1.setCnpj("11.222.333/0001-81");
                store1.setName("TechCars");
                store1.setDescription("New store here!");
                store1 = storeDAO.save(store1);
                logger.info("Store1 created with ID: {}", store1.getId());

                Store store2 = new Store();
                store2.setEmail("store2@example.com");
                store2.setPassword(passwordEncoder.encode("123"));
                store2.setRole(UserRoleEnum.STORE);
                store2.setEnabled(true);
                store2.setCnpj("44.556.677/0001-92");
                store2.setName("Tesla");
                store2.setDescription("Electric cars are our thing!");
                store2 = storeDAO.save(store2);
                logger.info("Store2 created with ID: {}", store2.getId());

                // Criar Veículos
                logger.info("Creating vehicles...");
                Vehicle vehicle1 = new Vehicle();
                vehicle1.setPlate("ABC1D23");
                vehicle1.setModel("Toyota Corolla");
                vehicle1.setChassi("9BWZZZ377VT004251");
                vehicle1.setYear(2020);
                vehicle1.setMileage(25000);
                vehicle1.setDescription("Excellent condition, single owner");
                vehicle1.setValue(new BigDecimal("85000.00"));
                vehicle1.setImages(List.of("http://localhost:8080/image/corolla.jpg"));
                vehicle1.setStore(store1);
                vehicle1 = vehicleDAO.save(vehicle1);
                logger.info("Vehicle1 created with ID: {}", vehicle1.getId());

                Vehicle vehicle2 = new Vehicle();
                vehicle2.setPlate("XYZ9E87");
                vehicle2.setModel("Honda Civic");
                vehicle2.setChassi("9BWZZZ377VT004252");
                vehicle2.setYear(2019);
                vehicle2.setMileage(40000);
                vehicle2.setDescription("Well maintained, all services done");
                vehicle2.setValue(new BigDecimal("75000.00"));
                vehicle2.setImages(List.of("http://localhost:8080/image/civic.jpg"));
                vehicle2.setStore(store1);
                vehicle2 = vehicleDAO.save(vehicle2);
                logger.info("Vehicle2 created with ID: {}", vehicle2.getId());

                Vehicle vehicle3 = new Vehicle();
                vehicle3.setPlate("ABC1234");
                vehicle3.setModel("Tesla Model 3");
                vehicle3.setChassi("5YJ3E1EAXJF000001");
                vehicle3.setYear(2021);
                vehicle3.setMileage(15000);
                vehicle3.setDescription("Premium electric vehicle, full options");
                vehicle3.setValue(new BigDecimal("250000.00"));
                vehicle3.setImages(List.of("http://localhost:8080/image/tesla.jpg"));
                vehicle3.setStore(store2);
                vehicle3 = vehicleDAO.save(vehicle3);
                logger.info("Vehicle3 created with ID: {}", vehicle3.getId());

                // Criar Ofertas
                logger.info("Creating offers...");
                Offer offer1 = new Offer();
                offer1.setValue(new BigDecimal("80000.00"));
                offer1.setPaymentConditions("A vista");
                offer1.setStatus(OfferStatus.OPEN);
                offer1.setVehicle(vehicle1);
                offer1.setClient(client1);
                offer1 = offerDAO.save(offer1);
                logger.info("Offer1 created with ID: {}", offer1.getId());

                Offer offer2 = new Offer();
                offer2.setValue(new BigDecimal("70000.00"));
                offer2.setPaymentConditions("12x parcelado");
                offer2.setStatus(OfferStatus.OPEN);
                offer2.setVehicle(vehicle2);
                offer2.setClient(client1);
                offer2 = offerDAO.save(offer2);
                logger.info("Offer2 created with ID: {}", offer2.getId());

                Offer offer3 = new Offer();
                offer3.setValue(new BigDecimal("230000.00"));
                offer3.setPaymentConditions("24x parcelado");
                offer3.setStatus(OfferStatus.ACCEPTED);
                offer3.setVehicle(vehicle3);
                offer3.setClient(client2);
                offer3 = offerDAO.save(offer3);
                logger.info("Offer3 created with ID: {}", offer3.getId());

                logger.info("Database seed completed successfully");
            } catch (Exception e) {
                logger.error("Error during database seed: {}", e.getMessage(), e);
            }
        };
    }
}