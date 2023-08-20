package TphonesShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import TphonesShop.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	OrderDetail findById(long id);

	void deleteByOrderId(long order_id);

	List<OrderDetail> getOrdersByProductId(long product_id);

	List<OrderDetail> getOrdersByOrderId(long order_id);

	@Query(value = "SELECT * FROM cse310.order_detail where order_id=?1 and product_id=?2", nativeQuery = true)
	OrderDetail getOrdersByProductId(long order_id, long product_id);
}
