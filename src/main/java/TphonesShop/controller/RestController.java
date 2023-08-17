package TphonesShop.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import TphonesShop.model.Brand;
import TphonesShop.model.Order;
import TphonesShop.model.OrderDetail;
import TphonesShop.model.User;
import TphonesShop.service.BrandService;
import TphonesShop.service.OrderDetailService;
import TphonesShop.service.OrderService;
import TphonesShop.service.ProductService;
import TphonesShop.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Resource
	UserService userService;
	@Resource
	ProductService productService;
	@Resource
	BrandService brandService;
	@Resource
	OrderService orderService;
	@Resource
	OrderDetailService orderDetailService;

	@PostMapping("/checkUser")
	public boolean checkUser(@RequestParam("username") String name) {
		User user = userService.findUserByUsername(name);
		if (user != null) {
			return true;
		}
		return false;
	}

	@PostMapping("/getBrandList")
	public List<Brand> geList() {
		return brandService.getBrandList();
	}

	@PostMapping("/get/brand/{id}")
	public Brand getBrandById(@PathVariable("id") long id) {
		return brandService.findBrandById(id);
	}

	@PostMapping("/add-to-cart")
	public boolean addToCart(HttpSession httpSession,
			@RequestParam("product_id") long product_id,
			@RequestParam("product_name") String product_name,
			@RequestParam("product_price") double price,
			@RequestParam(value = "quantity", defaultValue = "1") int quantity,
			Model model) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			List<Order> orders = orderService.getOrders(false, user.getUsername());
			if (orders.isEmpty()) {
				Order order = orderService.save(new Order(user.getUsername(), false));
				orderDetailService
						.save(new OrderDetail(order.getId(), product_id, product_name, quantity, price));
				return true;
			} else {
				orderDetailService
						.save(new OrderDetail(orders.get(0).getId(), product_id, product_name, quantity, price));
				return true;
			}
		}

		return false;
	}

	@PostMapping("/get-cart")
	public List<OrderDetail> getCart(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			List<Order> orders = orderService.getOrders(false, user.getUsername());
			if (!orders.isEmpty()) {
				return orderDetailService.getOrdersByOrderId(orders.get(0).getId());
			}
		}
		return null;
	}

	@PostMapping("/remove-cart")
	public boolean removeCart(@RequestParam("id") long id) {
		try {
			orderDetailService.delete(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
