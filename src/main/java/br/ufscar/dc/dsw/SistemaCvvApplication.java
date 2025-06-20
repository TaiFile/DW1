package br.ufscar.dc.dsw;

import br.ufscar.dc.dsw.dao.IAdminDAO;
import br.ufscar.dc.dsw.dao.IClientDAO;
import br.ufscar.dc.dsw.dao.IOfferDAO;
import br.ufscar.dc.dsw.dao.IStoreDAO;
import br.ufscar.dc.dsw.dao.IVehicleDAO;
import br.ufscar.dc.dsw.domain.Admin;
import br.ufscar.dc.dsw.domain.Client;
import br.ufscar.dc.dsw.domain.Offer;
import br.ufscar.dc.dsw.domain.Store;
import br.ufscar.dc.dsw.domain.Vehicle;
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
    public CommandLineRunner seed(IAdminDAO adminDAO, IClientDAO clientDAO, IStoreDAO storeDAO,
                                  IVehicleDAO vehicleDAO, IOfferDAO offerDAO,
                                  BCryptPasswordEncoder passwordEncoder) {
        return (args) -> {
            if (!Arrays.stream(args).toList().contains("--seed")) {
                logger.info("Database seed skiped");
                return;
            }

            offerDAO.deleteAll();
            vehicleDAO.deleteAll();
            adminDAO.deleteAll();
            clientDAO.deleteAll();
            storeDAO.deleteAll();

            Admin admin = new Admin();
            admin.setId(1L);
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setRole(UserRoleEnum.ADMIN);
            admin.setEnabled(true);
            adminDAO.save(admin);

            Client client1 = new Client();
            client1.setId(2L);
            client1.setEmail("client1@example.com");
            client1.setPassword(passwordEncoder.encode("234"));
            client1.setRole(UserRoleEnum.CLIENT);
            client1.setEnabled(true);
            client1.setCpf("12345678901");
            client1.setName("Carlos Eduardo");
            client1.setPhone("11997784512");
            client1.setSex(SexEnum.MALE);
            client1.setDateOfBirth(LocalDate.of(2000, 1, 8));
            clientDAO.save(client1);

            Client client2 = new Client();
            client2.setId(3L);
            client2.setEmail("client2@example.com");
            client2.setPassword(passwordEncoder.encode("345"));
            client2.setRole(UserRoleEnum.CLIENT);
            client2.setEnabled(true);
            client2.setCpf("02123456789");
            client2.setName("Amanda");
            client2.setPhone("11997123654");
            client2.setSex(SexEnum.FEMALE);
            client2.setDateOfBirth(LocalDate.of(2000, 7, 12));
            clientDAO.save(client2);

            Store store1 = new Store();
            store1.setId(4L);
            store1.setEmail("store1@example.com");
            store1.setPassword(passwordEncoder.encode("456"));
            store1.setRole(UserRoleEnum.STORE);
            store1.setEnabled(true);
            store1.setCnpj("11222333000181");
            store1.setName("TechCars");
            store1.setDescription("New store here!");
            storeDAO.save(store1);

            Store store2 = new Store();
            store2.setId(5L);
            store2.setEmail("store2@example.com");
            store2.setPassword(passwordEncoder.encode("567"));
            store2.setRole(UserRoleEnum.STORE);
            store2.setEnabled(true);
            store2.setCnpj("44556677000192");
            store2.setName("Tesla");
            store2.setDescription("Electric cars are our thing!");
            storeDAO.save(store2);

            Vehicle vehicle1 = new Vehicle();
            vehicle1.setId(6L);
            vehicle1.setPlate("ABC1D23");
            vehicle1.setModel("Toyota Corolla");
            vehicle1.setChassi("9BWZZZ377VT004251");
            vehicle1.setYear(2020);
            vehicle1.setMileage(25000);
            vehicle1.setDescription("Excellent condition, single owner");
            vehicle1.setValue(new BigDecimal("85000.00"));
            vehicle1.setImages(List.of("image1.jpg", "image2.jpg"));
            vehicle1.setStore(store1);
            vehicleDAO.save(vehicle1);

            Vehicle vehicle2 = new Vehicle();
            vehicle2.setId(7L);
            vehicle2.setPlate("XYZ9E87");
            vehicle2.setModel("Honda Civic");
            vehicle2.setChassi("9BWZZZ377VT004252");
            vehicle2.setYear(2019);
            vehicle2.setMileage(40000);
            vehicle2.setDescription("Well maintained, all services done");
            vehicle2.setValue(new BigDecimal("75000.00"));
            vehicle2.setImages(List.of("image3.jpg"));
            vehicle2.setStore(store1);
            vehicleDAO.save(vehicle2);

            Vehicle vehicle3 = new Vehicle();
            vehicle3.setId(8L);
            vehicle3.setPlate("TESLA1");
            vehicle3.setModel("Tesla Model 3");
            vehicle3.setChassi("5YJ3E1EAXJF000001");
            vehicle3.setYear(2021);
            vehicle3.setMileage(15000);
            vehicle3.setDescription("Premium electric vehicle, full options");
            vehicle3.setValue(new BigDecimal("250000.00"));
            vehicle3.setImages(List.of("image4.jpg", "image5.jpg", "image6.jpg"));
            vehicle3.setStore(store2);
            vehicleDAO.save(vehicle3);

            Offer offer1 = new Offer();
            offer1.setId(9L);
            offer1.setValue(new BigDecimal("80000.00"));
            offer1.setStatus(OfferStatus.OPEN);
            offer1.setVehicle(vehicle1);
            offer1.setClient(client1);
            offerDAO.save(offer1);

            Offer offer2 = new Offer();
            offer2.setId(10L);
            offer2.setValue(new BigDecimal("70000.00"));
            offer2.setStatus(OfferStatus.OPEN);
            offer2.setVehicle(vehicle2);
            offer2.setClient(client1);
            offerDAO.save(offer2);

            Offer offer3 = new Offer();
            offer3.setId(11L);
            offer3.setValue(new BigDecimal("230000.00"));
            offer3.setStatus(OfferStatus.ACCEPTED);
            offer3.setVehicle(vehicle3);
            offer3.setClient(client2);
            offerDAO.save(offer3);
        };
    }
}