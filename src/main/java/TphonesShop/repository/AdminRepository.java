package TphonesShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import TphonesShop.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	Admin findById(long id);
	Admin findByUsername(String username);
}
