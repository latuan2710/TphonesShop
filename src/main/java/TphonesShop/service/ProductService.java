package TphonesShop.service;

import java.util.List;

import TphonesShop.model.Product;

public interface ProductService {
	public List<Product> getProductList();

	public Product findProductById(long id);

	public void save(Product Product);

	public void edit(Product Product);

	public void delete(long id);
}
