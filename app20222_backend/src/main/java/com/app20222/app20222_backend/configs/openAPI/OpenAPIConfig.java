package com.app20222.app20222_backend.configs.openAPI;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan(basePackages = "com.app20222.app20222_backend.configs")
public class OpenAPIConfig {

    public static final String LOCAL_HOST_IP = "http://127.0.0.1:8082";

    public static final String LOCAL_HOST = "localhost:8082";

    public static final String DEFAULT_PATH = "/app20222/api/swagger-ui.html";

    private static final List<Server> lstServer =  List.of(new Server().url(LOCAL_HOST_IP).description("Local IP"),
                                                           new Server().url(LOCAL_HOST).description("Local Host"));

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().servers(lstServer)
                .info(new Info()
                        .contact(new Contact()
                                .email("chiendao1808@gmail.com")
                                .name("Chien Dao - Hanoi University of Science and Technology"))
                        .version("1.0.0"));
    }
}
