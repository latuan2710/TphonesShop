package CSE310.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import CSE310.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
	Brand findById(long id);
	Brand findByName(String name);
}
