package TphonesShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TphonesShop.model.Admin;
import TphonesShop.repository.AdminRepository;
import TphonesShop.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository AdminRepository;
	
	@Override
	public List<Admin> getAdminList() {
		return AdminRepository.findAll();
	}

	@Override
	public Admin findAdminById(long id) {
		// TODO Auto-generated method stub
		return AdminRepository.findById(id);
	}

	@Override
	public void save(Admin Admin) {
		AdminRepository.save(Admin);
	}

	@Override
	public void edit(Admin Admin) {
		AdminRepository.save(Admin);
		
	}

	@Override
	public void delete(long id) {
		AdminRepository.deleteById(id);
		
	}
	@Override
	public Admin findAdminByUsername(String username) {
		return AdminRepository.findByUsername(username);
	}
}
