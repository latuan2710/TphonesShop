package TphonesShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import TphonesShop.model.Admin;
import TphonesShop.repository.AdminRepository;

@SpringBootApplication
public class TphonesShopApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TphonesShopApplication.class, args);
	}

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		for (Admin admin : adminRepository.findAll()) {
			boolean isPasswordMatch = passwordEncoder.matches("admin", admin.getPassword());
			System.out.println(isPasswordMatch);
		}
	}

}
