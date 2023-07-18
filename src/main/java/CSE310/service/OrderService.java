package CSE310.service;

import java.util.List;

import CSE310.model.Order;

public interface OrderService {
	public List<Order> getOrderList();
	
	public List<Order> getOrdersByProduct(String product_name);

    public Order findOrderById(long id);

    public void save(Order Order);

    public void edit(Order Order);

    public void delete(long id);
}
