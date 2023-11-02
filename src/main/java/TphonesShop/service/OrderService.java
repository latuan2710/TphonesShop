package TphonesShop.service;

import java.util.List;

import TphonesShop.model.Order;

public interface OrderService {
    public List<Order> getOrderList();

    public List<Order> getOrderByStatus();

    public List<Order> getHistoryOrders(long user_id);

    public Order getCart(long user_id);

    public Order findOrderById(long id);

    public Order save(Order Order);

    public void delete(Order order);

    public void checkStatus();
}
