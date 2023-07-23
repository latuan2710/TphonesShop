package TphonesShop.service;

import java.util.List;

import TphonesShop.model.Brand;

public interface BrandService {
	public List<Brand> getBrandList();

    public Brand findBrandById(long id);
    
    public Brand findBrandByName(String name);

    public Brand save(Brand Brand);

    public void edit(Brand Brand);

    public void delete(long id);
}
