package sg.edu.nus.iss.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

// import io.swagger.v3.oas.annotations.security.*;

@Configuration
public class SpringFoxConfig {

    // Working
    @Bean
    public OpenAPI openApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("Title")
                        .description("my little API")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Darryl")
                                .url("http://localhost:8081")
                                .email("darryl1975@gmail.com"))
                        .termsOfService("TOC")
                        .license(new License().name("License").url("#")));
    }

    // @Bean
    // public Docket api() {
    // return new Docket(DocumentationType.SWAGGER_2)
    // .select()
    // .apis(RequestHandlerSelectors.any())
    // .paths(PathSelectors.any())
    // .build();
    // }

}
