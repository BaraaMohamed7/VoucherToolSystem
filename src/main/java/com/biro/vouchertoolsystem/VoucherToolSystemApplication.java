package com.biro.vouchertoolsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(info = @Info(title = "Voucher Tool API", version = "1.0", description = "API for voucher management"))

public class VoucherToolSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoucherToolSystemApplication.class, args);
    }

}
