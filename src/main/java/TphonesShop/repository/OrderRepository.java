package TphonesShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import TphonesShop.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Order findById(long id);

	@Query(value = "SELECT * FROM cse310.orders where status=false and username=?1 ", nativeQuery = true)
	public Order getCart(String username);

	@Query(value = "SELECT * FROM cse310.orders where status=true and username=?1 ", nativeQuery = true)
	public List<Order> getHistoryOrders(String username);
}
