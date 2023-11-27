package TphonesShop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import TphonesShop.model.Order;

public interface OrderService {
    public List<Order> getOrderList();

    public List<Order> getOrdersBuyed();

    public Page<Order> getHistoryOrders(Pageable pageable, long user_id);

    public Order getCart(long user_id);

    public Order findOrderById(long id);

    public Order save(Order Order);

    public void delete(Order order);

    public void checkStatus();
}
