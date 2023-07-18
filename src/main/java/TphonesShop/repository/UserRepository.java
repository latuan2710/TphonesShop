package TphonesShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import TphonesShop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findById(long id);
	User findByUsername(String username);
}
