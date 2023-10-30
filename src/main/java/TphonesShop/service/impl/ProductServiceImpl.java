package TphonesShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import TphonesShop.model.Product;
import TphonesShop.repository.ProductRepository;
import TphonesShop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public List<Product> getAll() {
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
	public Product findProductByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public Page<Product> findByKey(String key, Pageable pageable) {
		return productRepository.findByNameContains(key, pageable);
	}

	@Override
	public List<Product> getSaleProducts() {
		return productRepository.getSaleProducts();
	}

	@Override
	public List<Product> getNewestProducts() {
		return productRepository.newestProducts();
	}

	@Override
	public Page<Product> findAllInPage(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public Page<Product> findByBrand(String[] brand, Pageable pageable) {
		return productRepository.findByBrandName(brand, pageable);
	}

	@Override
	public Page<Product> findByBrandAndKey(String[] brand, Pageable pageable, String key) {
		return productRepository.findByBrandAndKey(brand, pageable, key);
	}

	@Override
	public Page<Product> findByPrice(Pageable pageable, int minPrice, int maxPrice) {
		return productRepository.findByPriceBetween(pageable, minPrice, maxPrice);
	}

	@Override
	public Page<Product> findByPriceAndBrand(Pageable pageable, int minPrice, int maxPrice, String[] brand) {
		return productRepository.findByPriceAndBrand(pageable, minPrice, maxPrice, brand);
	}

	@Override
	public Page<Product> findByPriceAndKey(Pageable pageable, int minPrice, int maxPrice, String key) {
		return productRepository.findByPriceAndKey(pageable, minPrice, maxPrice, key);
	}

	@Override
	public Page<Product> findByPriceAndKeyAndBrand(Pageable pageable, int minPrice, int maxPrice, String key,
			String[] brand) {
		return productRepository.findByPriceAndKeyAndBrand(pageable, minPrice, maxPrice, key, brand);
	}
}
