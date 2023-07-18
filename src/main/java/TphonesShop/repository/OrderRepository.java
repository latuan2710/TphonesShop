package TphonesShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import TphonesShop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Order findById(long id);

	@Query(value = "SELECT * FROM TphonesShop.orders where product=?1", nativeQuery = true)
	public List<Order> getOrdersByProduct(String product_name);
}
