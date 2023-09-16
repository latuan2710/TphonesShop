package TphonesShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import TphonesShop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findById(long id);

	Product findByName(String name);

	@Query(value = "SELECT * FROM cse310.products WHERE discount>0 limit 10", nativeQuery = true)
	List<Product> getSaleProducts();

	@Query(value = "SELECT * FROM cse310.products WHERE name LIKE %?1%", nativeQuery = true)
	List<Product> searchProducts(String key);

	@Query(value = "SELECT * FROM cse310.products order by id desc limit 10", nativeQuery = true)
	List<Product> newestProducts();
}
