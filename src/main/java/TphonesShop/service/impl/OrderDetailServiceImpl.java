package TphonesShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TphonesShop.model.OrderDetail;
import TphonesShop.repository.OrderDetailRepository;
import TphonesShop.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Override
	public List<OrderDetail> getOrdersByOrderId(long order_id) {
		return orderDetailRepository.findByOrderId(order_id);
	}

	@Override
	public OrderDetail save(OrderDetail orderDetail) {
		return orderDetailRepository.save(orderDetail);
	}

	@Override
	public void delete(long id) {
		orderDetailRepository.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteByOrderId(long order_id) {
		orderDetailRepository.deleteByOrderId(order_id);
	}

	@Override
	public OrderDetail getOrdersByProductId(long order_id, long product_id) {
		return orderDetailRepository.findByOrderIdAndProductId(order_id, product_id);
	}
}
