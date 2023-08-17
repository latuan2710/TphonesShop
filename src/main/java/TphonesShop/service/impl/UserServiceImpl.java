package TphonesShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TphonesShop.model.User;
import TphonesShop.repository.UserRepository;
import TphonesShop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getUserList() {
		return userRepository.findAll();
	}

	@Override
	public User findUserById(long id) {
		return userRepository.findById(id);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public void delete(long id) {
		userRepository.deleteById(id);

	}

	@Override
	public User findUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<String> getListName() {
		return userRepository.getListName();
	}
}
