package br.ufscar.dc.dsw;


import br.ufscar.dc.dsw.dao.IAdminDAO;
import br.ufscar.dc.dsw.dao.IClientDAO;
import br.ufscar.dc.dsw.dao.IStoreDAO;
import br.ufscar.dc.dsw.domain.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;


@SpringBootApplication
@EnableAsync
public class SistemaCvvApplication {

    private static final Logger logger = LoggerFactory.getLogger(SistemaCvvApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SistemaCvvApplication.class, args);
    }

    @Bean
    public CommandLineRunner seed(
            IAdminDAO adminDao,
            IClientDAO clientDAO,
            IStoreDAO storeDAO,
            BCryptPasswordEncoder passwordEncoder) {
        return (args) -> {
            if (!Arrays.stream(args).toList().contains("--seed")) {
                logger.info("Database seed skiped");
                return;
            }

            adminDao.deleteAll();
            clientDAO.deleteAll();

            Admin admin = new Admin();
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("123"));

            adminDao.save(admin);
        };
    }
}
