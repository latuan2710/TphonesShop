package TphonesShop.service;

import java.util.List;

import TphonesShop.model.Admin;

public interface AdminService {
	public List<Admin> getAdminList();

    public Admin findAdminById(long id);
    
    public Admin findAdminByUsername(String username);

    public void save(Admin Admin);

    public void edit(Admin Admin);

    public void delete(long id);
}
