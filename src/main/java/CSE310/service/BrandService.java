package CSE310.service;

import java.util.List;

import CSE310.model.Brand;

public interface BrandService {
	public List<Brand> getBrandList();

    public Brand findBrandById(long id);
    
    public Brand findBrandByName(String name);

    public void save(Brand Brand);

    public void edit(Brand Brand);

    public void delete(long id);
}
