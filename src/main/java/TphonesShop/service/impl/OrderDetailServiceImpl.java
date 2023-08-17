package TphonesShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TphonesShop.model.OrderDetail;
import TphonesShop.repository.OrderDetailRepository;
import TphonesShop.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailRepository orderRepository;

	@Override
	public List<OrderDetail> getOrderList() {
		return orderRepository.findAll();
	}

	@Override
	public List<OrderDetail> getOrdersByOrderId(long order_id) {
		return orderRepository.getOrdersByOrderId(order_id);
	}

	@Override
	public OrderDetail save(OrderDetail orderDetail) {
		return orderRepository.save(orderDetail);
	}

	@Override
	public void delete(long id) {
		orderRepository.deleteById(id);
	}

}
