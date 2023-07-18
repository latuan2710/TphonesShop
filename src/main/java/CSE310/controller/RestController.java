package CSE310.controller;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import CSE310.model.Brand;
import CSE310.model.Order;
import CSE310.model.Product;
import CSE310.model.User;
import CSE310.service.BrandService;
import CSE310.service.OrderService;
import CSE310.service.ProductService;
import CSE310.service.UserService;
import jakarta.annotation.Resource;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@Resource
	BrandService brandService;
	@Resource
	OrderService orderService;
	@Resource
	ProductService productService;
	@Resource
	UserService userService;

	@GetMapping(value = "/get/user/{id}")
	public User getUser(@PathVariable int id) {
		return userService.findUserById(id);
	}

	@GetMapping(value = "/get/all/user")
	public List<User> getAllUsers() {
		return userService.getUserList();
	}

	@GetMapping(value = "/get/all/order")
	public List<Order> getAllOrders() {
		return orderService.getOrderList();
	}

	@GetMapping(value = "/add-to-cart")
	public String ok(@Param("text") String text) {
		return text;
	}
}
