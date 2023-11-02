package TphonesShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import TphonesShop.model.Token;

import java.time.LocalDateTime;

public interface TokenRepository extends JpaRepository<Token, Long> {
	Token findByToken(String token);

	void deleteByUserId(long user_id);	
	
	void deleteByExpirationTimeBefore(LocalDateTime expirationTime);

}
