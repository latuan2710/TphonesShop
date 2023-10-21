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

	@Query(value = "SELECT * FROM cse310.products order by id desc limit 10", nativeQuery = true)
	List<Product> newestProducts();

	Page<Product> findByNameContains(String name, Pageable pageable);

	Page<Product> findByPriceBetween(Pageable pageable, int minPrice, int maxPrice);

	@Query("Select p From Product p where p.brand.name in :brands")
	Page<Product> findByBrandName(@Param("brands") String[] brand, Pageable pageable);

	@Query("Select p From Product p where p.brand.name in :brands and p.name like %:key% ")
	Page<Product> findByBrandAndKey(@Param("brands") String[] brand, Pageable pageable, @Param("key") String key);

	@Query("SELECT p FROM Product p WHERE (p.price BETWEEN :minPrice AND :maxPrice) and (p.brand.name in :brands)")
	Page<Product> findByPriceAndBrand(Pageable pageable, int minPrice, int maxPrice, @Param("brands") String[] brand);

	@Query("SELECT p FROM Product p WHERE (p.price BETWEEN :minPrice AND :maxPrice) and (p.name like %:key%)")
	Page<Product> findByPriceAndKey(Pageable pageable, int minPrice, int maxPrice, @Param("key") String key);

	@Query("SELECT p FROM Product p WHERE " +
			"(p.price BETWEEN :minPrice AND :maxPrice) and" +
			" (p.name like %:key%) and" +
			" (p.brand.name in :brands)")
	Page<Product> findByPriceAndKeyAndBrand(Pageable pageable, int minPrice, int maxPrice, @Param("key") String key,
			@Param("brands") String[] brand);

}
