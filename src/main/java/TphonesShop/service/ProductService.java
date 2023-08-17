package TphonesShop.service;

import java.util.List;

import TphonesShop.model.Product;

public interface ProductService {
	public List<Product> getProductList();

	public List<Product> getProductsByBrand(String brand_name);

	public Product findProductById(long id);

	public Product findProductByName(String name);

	public Product save(Product Product);

	public void delete(long id);
}
