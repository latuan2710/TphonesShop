package CSE310.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CSE310.model.Brand;
import CSE310.repository.BrandRepository;
import CSE310.service.BrandService;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandRepository brandRepository;
	
	@Override
	public List<Brand> getBrandList() {
		return brandRepository.findAll();
	}

	@Override
	public Brand findBrandById(long id) {
		return brandRepository.findById(id);
	}

	@Override
	public void save(Brand brand) {
		brandRepository.save(brand);
	}

	@Override
	public void edit(Brand brand) {
		brandRepository.save(brand);
		
	}

	@Override
	public void delete(long id) {
		brandRepository.deleteById(id);
		
	}

	@Override
	public Brand findBrandByName(String name) {
		return brandRepository.findByName(name);
	}

}
