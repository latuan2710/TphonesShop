package TphonesShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import TphonesShop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findById(long id);

	@Query(value = "SELECT * FROM cse310.products where brand=?1", nativeQuery = true)
	public List<Product> getProductsByBrand(String brand_name);
}
