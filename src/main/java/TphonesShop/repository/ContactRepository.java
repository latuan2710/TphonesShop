package TphonesShop.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import TphonesShop.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	Contact findById(long id);

	List<Contact> findByStatus(boolean status, Sort sort);
}
