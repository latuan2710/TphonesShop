package TphonesShop;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
public class TphonesShopApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(TphonesShopApplication.class, args);
	}
}
