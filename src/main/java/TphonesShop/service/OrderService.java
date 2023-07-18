package TphonesShop.service;

import java.util.List;

import TphonesShop.model.Order;

public interface OrderService {
	public List<Order> getOrderList();
	
	public List<Order> getOrdersByProduct(String product_name);

    public Order findOrderById(long id);

    public void save(Order Order);

    public void edit(Order Order);

    public void delete(long id);
}
