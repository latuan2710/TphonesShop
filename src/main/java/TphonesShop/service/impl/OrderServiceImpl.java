package TphonesShop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import TphonesShop.model.Order;
import TphonesShop.repository.OrderRepository;
import TphonesShop.service.OrderService;
import jakarta.transaction.Transactional;

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
	@Transactional
	public void delete(Order order) {
		orderRepository.delete(order);
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
		return orderRepository.findByUserIdAndStatus(user_id, 0);
	}

	@Override
	public List<Order> getOrdersBuyed() {
		List<Order> result = orderRepository.findByStatus(1, Sort.by("id").descending());

		result.addAll(orderRepository.findByStatus(2, Sort.by("id").descending()));
		result.addAll(orderRepository.findByStatus(3, Sort.by("id").ascending()));
		result.addAll(orderRepository.findByStatus(-1, Sort.by("id").ascending()));
		result.addAll(orderRepository.findByStatus(-2, Sort.by("id").ascending()));

		return result;
	}

	@Override
	@Scheduled(fixedRate = 1800000)
	@Transactional
	public void checkStatus() {
		orderRepository.deleteByStatus(5);
	}

}
