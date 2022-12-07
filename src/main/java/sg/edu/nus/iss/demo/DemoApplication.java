package sg.edu.nus.iss.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

// import io.swagger.v3.oas.annotations.security.SecurityScheme;
// import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
// import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

@EnableCaching
@SpringBootApplication
// @SecurityScheme(name = "darryltechroom-api", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
// @CrossOrigin(origins = "http://localhost:8081")
@OpenAPIDefinition(servers = {
	@Server(url = "http://localhost:8081", description = "embedded API webserver"),
	@Server(url = "http://192.168.1.116:8081", description = "localhost API webserver")
})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
