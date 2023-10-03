package TphonesShop;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories
public class TphonesShopApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(TphonesShopApplication.class, args);
		openHomePage();
	}

	private static void openHomePage() throws IOException {
		Runtime rt = Runtime.getRuntime();
		rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8080");
	}
}
