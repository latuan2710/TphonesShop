package TphonesShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TphonesShop.model.Product;
import TphonesShop.repository.ProductRepository;
import TphonesShop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getProductList() {
		return productRepository.findAll();
	}

	@Override
	public Product findProductById(long id) {
		return productRepository.findById(id);
	}

	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void delete(long id) {
		productRepository.deleteById(id);

	}

	@Override
	@Transactional
	public List<Product> getProductsByBrand(String brand_name) {
		return productRepository.getProductsByBrand(brand_name);
	}

	@Override
	@Transactional
	public Product findProductByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> searchProducts(String key) {
		return productRepository.searchProducts(key);
	}
}
