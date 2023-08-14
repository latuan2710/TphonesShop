package TphonesShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import TphonesShop.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	Contact findById(long id);
}
