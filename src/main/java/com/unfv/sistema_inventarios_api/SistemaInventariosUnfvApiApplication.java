package com.unfv.sistema_inventarios_api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

@SpringBootApplication
@Slf4j
public class SistemaInventariosUnfvApiApplication {

    public static void main(String[] args) {
        Instant instante = Instant.now();
        log.info("Instante: " + instante.toString());
        SpringApplication.run(SistemaInventariosUnfvApiApplication.class, args);
    }

}
