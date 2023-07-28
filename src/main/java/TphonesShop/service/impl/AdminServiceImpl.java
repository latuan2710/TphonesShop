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
	private AdminRepository adminRepository;

	@Override
	public List<Admin> getAdminList() {
		return adminRepository.findAll();
	}

	@Override
	public Admin findAdminById(long id) {
		// TODO Auto-generated method stub
		return adminRepository.findById(id);
	}

	@Override
	public void save(Admin Admin) {
		adminRepository.save(Admin);
	}

	@Override
	public void delete(long id) {
		adminRepository.deleteById(id);

	}

	@Override
	public Admin findAdminByUsername(String username) {
		return adminRepository.findByUsername(username);
	}

	@Override
	public List<String> getListName() {
		return adminRepository.getListName();
	}
}
