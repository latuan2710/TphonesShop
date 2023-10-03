package TphonesShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import TphonesShop.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Order findById(long id);

	Order findByUserIdAndStatus(long user_id, int status);

	List<Order> findByStatus(int status);

	@Query(value = "SELECT * FROM cse310.orders where status=2 and username=?1 ", nativeQuery = true)
	List<Order> getHistoryOrders(long user_id);
}
