package TphonesShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import TphonesShop.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
	Brand findById(long id);
	Brand findByName(String name);
}
