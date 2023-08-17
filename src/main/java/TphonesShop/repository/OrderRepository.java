package TphonesShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import TphonesShop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Order findById(long id);

	@Query(value = "SELECT * FROM cse310.orders where status=?1 and username=?2 ", nativeQuery = true)
	public List<Order> getOrders(boolean status, String username);
}
