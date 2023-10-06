package TphonesShop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

	@Query("Select p From Product p where p.brand.name in :brands")
	Page<Product> findByBrandName(@Param("brands") String[] brand, Pageable pageable);
}
