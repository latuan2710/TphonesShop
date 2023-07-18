package CSE310.service;

import java.util.List;

import CSE310.model.User;

public interface UserService {
	public List<User> getUserList();

    public User findUserById(long id);
    
    public User findUserByUsername(String username);

    public void save(User user);

    public void edit(User user);

    public void delete(long id);
}
