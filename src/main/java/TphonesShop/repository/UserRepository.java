package TphonesShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import TphonesShop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findById(long id);

	User findByUsername(String username);

	User findByEmail(String email);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE " +
			"(u.username = :key) or " +
			"(u.email = :key) or " +
			"(u.phone = :key)")
	boolean checkExist(@Param("key") String key);

    List<User> findAllByType(String type);
}
