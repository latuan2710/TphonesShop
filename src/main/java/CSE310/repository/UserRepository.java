package CSE310.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import CSE310.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findById(long id);
	User findByUsername(String username);
}
