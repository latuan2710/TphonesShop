package CSE310.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CSE310.model.Order;
import CSE310.repository.OrderRepository;
import CSE310.service.OrderService;

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
		// TODO Auto-generated method stub
		return orderRepository.findById(id);
	}

	@Override
	public void save(Order order) {
		orderRepository.save(order);
	}

	@Override
	public void edit(Order order) {
		orderRepository.save(order);
		
	}

	@Override
	public void delete(long id) {
		orderRepository.deleteById(id);
		
	}

	@Override
	public List<Order> getOrdersByProduct(String product_name) {
		return orderRepository.getOrdersByProduct(product_name);
	}

}
