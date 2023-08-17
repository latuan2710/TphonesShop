package TphonesShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import TphonesShop.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	OrderDetail findById(long id);

	@Query(value = "SELECT * FROM cse310.order_detail where order_id=?1", nativeQuery = true)
	public List<OrderDetail> getOrdersByOrderId(long order_id);
}
