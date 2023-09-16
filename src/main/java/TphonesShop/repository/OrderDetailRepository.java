package TphonesShop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import TphonesShop.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	OrderDetail findById(long id);

	void deleteByOrderId(long order_id);

	List<OrderDetail> findByOrderId(long order_id);

	OrderDetail findByOrderIdAndProductId(long order_id, long product_id);
}
