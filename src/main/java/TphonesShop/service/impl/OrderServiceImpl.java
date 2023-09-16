package TphonesShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TphonesShop.model.Order;
import TphonesShop.repository.OrderRepository;
import TphonesShop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<Order> getOrderList() {
		return orderRepository.findAll();
	}

	@Override
	public Order findOrderById(long id) {
		return orderRepository.findById(id);
	}

	@Override
	public void delete(long id) {
		orderRepository.deleteById(id);

	}

	@Override
	public Order save(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public List<Order> getHistoryOrders(long user_id) {
		return orderRepository.getHistoryOrders(user_id);
	}

	@Override
	public Order getCart(long user_id) {
		return orderRepository.findByUserIdAndStatus(user_id, false);
	}

}
