package TphonesShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// TODO Auto-generated method stub
		return productRepository.findById(id);
	}

	@Override
	public void save(Product product) {
		productRepository.save(product);
	}

	@Override
	public void edit(Product product) {
		productRepository.save(product);

	}

	@Override
	public void delete(long id) {
		productRepository.deleteById(id);

	}
}
