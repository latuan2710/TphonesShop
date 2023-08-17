package TphonesShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TphonesShop.model.Contact;
import TphonesShop.repository.ContactRepository;
import TphonesShop.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	ContactRepository contactRepository;

	@Override
	public void delete(long id) {
		contactRepository.deleteById(id);
	}

	@Override
	public Contact findContactById(long id) {
		return contactRepository.findById(id);
	}

	@Override
	public List<Contact> getContactList() {
		return contactRepository.findAll();
	}

	@Override
	public Contact save(Contact contact) {
		return contactRepository.save(contact);
	}

}
