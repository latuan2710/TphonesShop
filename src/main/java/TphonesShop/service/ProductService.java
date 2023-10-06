package TphonesShop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import TphonesShop.model.Product;

public interface ProductService {
	public List<Product> getProductList();

	public Page<Product> getProductListbyPage(Pageable pageable);

	public Page<Product> findByBrandName(String[] brand, Pageable pageable);

	public List<Product> getSaleProducts();

	public List<Product> getNewestProducts();

	public List<Product> searchProducts(String key);

	public Product findProductById(long id);

	public Product findProductByName(String name);

	public Product save(Product Product);

	public void delete(long id);
}
