package TphonesShop.service;

import java.util.List;

import TphonesShop.model.User;

public interface UserService {
	public List<User> getUserList();

	public List<User> getAdminList();

	public User findUserById(long id);

	public User findUserByUsername(String username);

	public User findUserByEmail(String email);

	public void save(User user);

	public void delete(long id);

	public boolean checkExist(String key);
}
