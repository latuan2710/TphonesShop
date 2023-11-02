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

	@Query(value = "SELECT * FROM cse310.orders where status!=0", nativeQuery = true)
	List<Order> getOrdersBuyed();

	@Query(value = "SELECT * FROM cse310.orders where status!=0 and user_id=?1 ", nativeQuery = true)
	List<Order> getHistoryOrders(long user_id);

	void deleteByStatus(int status);
}
