package TphonesShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import TphonesShop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findById(long id);

	User findByUsername(String username);

	@Query(value = "SELECT username FROM cse310.users", nativeQuery = true)
	List<String> getListName();
}
