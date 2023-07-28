package TphonesShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import TphonesShop.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	Admin findById(long id);

	Admin findByUsername(String username);

	@Query(value = "SELECT username FROM cse310.admins", nativeQuery = true)
	List<String> getListName();
}
