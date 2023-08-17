package TphonesShop.service;

import java.util.List;

import TphonesShop.model.OrderDetail;

public interface OrderDetailService {
    public List<OrderDetail> getOrderList();

    public List<OrderDetail> getOrdersByOrderId(long order_id);

    public OrderDetail save(OrderDetail orderDetail);

    public void delete(long id);
}
