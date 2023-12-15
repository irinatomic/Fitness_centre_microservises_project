package raf.fitness.user_servis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class UserServisApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServisApplication.class, args);
	}

}
