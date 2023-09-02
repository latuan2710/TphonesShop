package TphonesShop.service;

import java.util.List;

import TphonesShop.model.User;

public interface UserService {
	public List<User> getUserList();

	public User findUserById(long id);

	public User findUserByUsername(String username);

	public void save(User user);

	public void delete(long id);

	public List<String> getListName();

	public List<String> getListEmail();

	public List<String> getListPhone();
}
