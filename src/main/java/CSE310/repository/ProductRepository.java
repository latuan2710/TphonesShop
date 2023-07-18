package CSE310.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import CSE310.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Product findById(long id);
}
