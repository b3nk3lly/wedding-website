package ca.b3nk3lly.wedding_website_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WeddingWebsiteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeddingWebsiteBackendApplication.class, args);
	}

}
