package TphonesShop.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import TphonesShop.model.Brand;
import TphonesShop.model.Order;
import TphonesShop.model.OrderDetail;
import TphonesShop.model.Product;
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

	@GetMapping("/check")
	public boolean check(@RequestParam String key) {
		return userService.checkExist(key);
	}

	@GetMapping("/getBrandList")
	public List<Brand> geList() {
		return brandService.getBrandList();
	}

	@PostMapping("/get/brand/{id}")
	public Brand getBrandById(@PathVariable long id) {
		return brandService.findBrandById(id);
	}

	@PostMapping("/update-cart")
	public boolean updateCart(HttpSession httpSession,
			@RequestBody HashMap<String, Integer>[] data) {

		try {
			User user = (User) httpSession.getAttribute("user");
			Order order = orderService.getCart(user.getId());

			double total = order.getTotalPrice();
			for (HashMap<String, Integer> oH : data) {
				OrderDetail orderDetail = orderDetailService.getOrdersByProductId(
						order.getId(), oH.get("productId"));
				total -= orderDetail.getFinalPrice();
				if (oH.get("quantity") == 0) {
					orderDetailService.delete(orderDetail.getId());
					continue;
				}
				orderDetail.setQuantity(oH.get("quantity"));
				orderDetail.setFinalPrice();
				orderDetailService.save(orderDetail);
				total += orderDetail.getFinalPrice();
			}
			if (order.getOrderDetails().size() == 0) {
				orderService.delete(order);
				return true;
			}
			order.setTotalPrice(total);
			orderService.save(order);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@PostMapping("/add-to-cart")
	public boolean addToCart(HttpSession httpSession,
			@RequestParam long product_id,
			@RequestParam(defaultValue = "1") int quantity) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			Order order = orderService.getCart(user.getId());
			Product product = productService.findProductById(product_id);
			OrderDetail orderDetail;

			double total = 0;
			if (order == null) {
				order = orderService.save(new Order(user, 0));
				orderDetail = new OrderDetail(order, product, quantity, product.getFinal_price());
			} else {
				total = order.getTotalPrice();
				orderDetail = orderDetailService.getOrdersByProductId(order.getId(), product_id);
				if (orderDetail == null) {
					orderDetail = new OrderDetail(order, product, quantity, product.getFinal_price());
				} else {
					total -= orderDetail.getFinalPrice();
					orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
					orderDetail.setFinalPrice();
				}
			}
			total += orderDetail.getFinalPrice();
			order.setTotalPrice(total);
			orderDetailService.save(orderDetail);
			return true;
		}
		return false;
	}

	@PostMapping("/get-cart")
	public List<OrderDetail> getCart(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			Order order = orderService.getCart(user.getId()); 
			if (order != null) {
				return orderDetailService.getOrdersByOrderId(order.getId());
			}
		}
		return null;
	}

	@PostMapping("/remove-cart-item")
	public boolean removeCartItem(@RequestParam long id) {
		try {
			OrderDetail orderDetail = orderDetailService.findById(id);
			Order order = orderDetail.getOrder();

			double total = order.getTotalPrice() - orderDetail.getFinalPrice();
			order.setTotalPrice(total);

			orderDetailService.delete(id);

			if (order.getOrderDetails().size() == 0)
				orderService.delete(order);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@PostMapping("/clear-cart")
	public boolean clearCart(HttpSession httpSession) {
		try {
			User user = (User) httpSession.getAttribute("user");
			Order order = orderService.getCart(user.getId());

			orderService.delete(order);

			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@PostMapping("/checkout")
	public boolean buyOrder(
			@RequestParam("order_id") int id,
			@RequestParam("products_name[]") String[] products_name,
			@RequestParam("products_quantity[]") int[] products_quantity,
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String phone,
			@RequestParam String address) {

		for (int i = 0; i < products_quantity.length; i++) {
			Product product = productService.findProductByName(products_name[i]);
			product.setQuantity(product.getQuantity() - products_quantity[i]);
			productService.save(product);
		}

		Order order = orderService.findOrderById(id);
		order.setStatus(1);
		order.setReceiverName(name);
		order.setReceiverEmail(email);
		order.setReceiverPhone(phone);
		order.setReceiverAddress(address);
		order.setCreatedDateTime(LocalDateTime.now());
		orderService.save(order);

		return true;
	}

	@PostMapping("/search")
	public List<Product> searchProducts(@RequestParam String key) {
		Pageable paging = PageRequest.of(0, 10, Sort.by("id").descending());
		return productService.findByKey(key, paging).getContent();
	}

	@GetMapping("/orderDetails/orderId/{order_id}")
	public List<OrderDetail> getOrderDetailsByOrderId(@PathVariable("order_id") long id) {
		return orderDetailService.getOrdersByOrderId(id);
	}

	@PostMapping("/user/{user_id}/update")
	public boolean updateUserDetail(
			HttpSession session,
			@PathVariable("user_id") long id,
			@RequestParam String name,
			@RequestParam String value) {
		User user = userService.findUserById(id);

		try {
			switch (name) {
				case "dateOfBirth":
					user.setDateOfBirth(value);
					break;
				case "email":
					user.setEmail(value);
					break;
				case "phone":
					user.setPhone(value);
					break;
				case "address":
					user.setAddress(value);
					break;

				default:
					return false;
			}
		} catch (Exception e) {
			return false;
		}

		session.setAttribute("user", user);
		userService.save(user);
		return true;
	}
}
