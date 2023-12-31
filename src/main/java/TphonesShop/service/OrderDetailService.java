package TphonesShop.service;

import java.util.List;

import TphonesShop.model.OrderDetail;

public interface OrderDetailService {

    public OrderDetail findById(long id);

    public OrderDetail getOrdersByProductId(long order_id, long product_id);

    public OrderDetail save(OrderDetail orderDetail);

    public List<OrderDetail> getOrdersByOrderId(long order_id);

    public void delete(long id);
}
