package TphonesShop.service;

import java.util.List;

import TphonesShop.model.Order;

public interface OrderService {
    public List<Order> getOrderList();

    public List<Order> getHistoryOrders(String username);

    public Order getCart(String username);

    public Order findOrderById(long id);

    public Order save(Order Order);

    public void delete(long id);
}
