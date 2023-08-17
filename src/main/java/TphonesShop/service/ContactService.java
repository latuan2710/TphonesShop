package TphonesShop.service;

import java.util.List;

import TphonesShop.model.Contact;

public interface ContactService {
	public List<Contact> getContactList();

    public Contact findContactById(long id);

    public Contact save(Contact contact);

    public void delete(long id);
}
