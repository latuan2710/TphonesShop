package TphonesShop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import TphonesShop.model.Product;

public interface ProductService {
	public List<Product> getProductList();

	public Page<Product> findAllInPage(Pageable pageable);

	public Page<Product> findByBrand(String[] brand, Pageable pageable);

	public Page<Product> findByKey(String key, Pageable pageable);

	public Page<Product> findByPrice(Pageable pageable, int minPrice, int maxPrice);

	public Page<Product> findByBrandAndKey(String[] brand, Pageable pageable, String key);

	public Page<Product> findByPriceAndBrand(Pageable pageable, int minPrice, int maxPrice, String[] brand);

	public Page<Product> findByPriceAndKey(Pageable pageable, int minPrice, int maxPrice, @Param("key") String key);

	public Page<Product> findByPriceAndKeyAndBrand(Pageable pageable, int minPrice, int maxPrice, String key,
			String[] brand);

	public List<Product> getSaleProducts();

	public List<Product> getNewestProducts();

	public Product findProductById(long id);

	public Product findProductByName(String name);

	public Product save(Product Product);

	public void delete(long id);
}
