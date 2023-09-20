package TphonesShop.controller;

import java.util.HashMap;
import java.util.List;

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

	@PostMapping("/check/username")
	public boolean checkUsername(@RequestParam("username") String username) {
		List<String> list = userService.getListName();
		if (list.contains(username)) {
			return true;
		}
		return false;
	}

	@PostMapping("/check/email")
	public boolean checkEmail(@RequestParam("email") String email) {
		List<String> list = userService.getListEmail();
		if (list.contains(email)) {
			return true;
		}
		return false;
	}

	@PostMapping("/check/phone")
	public boolean checkPhone(@RequestParam("phone") String phone) {
		List<String> list = userService.getListPhone();
		if (list.contains(phone)) {
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

	@PostMapping("/buy-now")
	public boolean BuyNow(HttpSession httpSession,
			@RequestParam("product_id") long product_id,
			@RequestParam(value = "quantity", defaultValue = "1") int quantity) {

		User user = (User) httpSession.getAttribute("user");

		if (user != null) {

			Product product = productService.findProductById(product_id);

			Order order = orderService.save(new Order(user, true));

			orderDetailService.save(new OrderDetail(order, product, quantity, product.getFinal_price()));

			product.setQuantity(product.getQuantity() - quantity);
			productService.save(product);

			orderService.save(order);

			return true;
		}

		return false;
	}

	@PostMapping("/buy-all")
	public boolean BuyAll(HttpSession httpSession) {

		User user = (User) httpSession.getAttribute("user");

		if (user != null) {
			Order order = orderService.getCart(user.getId());

			// Update quantity of product
			List<OrderDetail> orderDetails = order.getOrderDetails();
			for (OrderDetail orderDetail : orderDetails) {
				Product product = orderDetail.getProduct();
				product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
			}

			order.setStatus(true);
			orderService.save(order);

			return true;
		}

		return false;
	}

	@PostMapping("/update-cart")
	public boolean updateCart(HttpSession httpSession,
			@RequestBody HashMap<String, Integer>[] data) {

		System.out.println(data[0].get("productId"));
		try {
			User user = (User) httpSession.getAttribute("user");

			Order order = orderService.getCart(user.getId());

			for (HashMap<String, Integer> oH : data) {
				OrderDetail orderDetail = orderDetailService.getOrdersByProductId(
						order.getId(), oH.get("productId"));
				orderDetail.setQuantity(oH.get("quantity"));
				orderDetail.setFinalPrice();
				orderDetailService.save(orderDetail);
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@PostMapping("/add-to-cart")
	public boolean addToCart(HttpSession httpSession,
			@RequestParam("product_id") long product_id,
			@RequestParam(value = "quantity", defaultValue = "1") int quantity) {

		User user = (User) httpSession.getAttribute("user");

		if (user != null) {

			Order order = orderService.getCart(user.getId());
			Product product = productService.findProductById(product_id);
			OrderDetail orderDetail;

			if (order == null) {
				order = orderService.save(new Order(user, false));
				orderDetail = new OrderDetail(order, product, quantity, product.getFinal_price());
			} else {
				orderDetail = orderDetailService.getOrdersByProductId(order.getId(), product_id);
				orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
				orderDetail.setFinalPrice();
			}

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

	@PostMapping("/remove-cart")
	public boolean removeCartItem(@RequestParam("id") long id) {
		try {
			orderDetailService.delete(id);
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

			orderDetailService.deleteByOrderId(order.getId());
			orderService.delete(order.getId());

			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	@PostMapping("/search")
	public List<Product> searchProducts(@RequestParam("key") String key) {
		return productService.searchProducts(key);
	}

	@GetMapping("/orderDetails/orderId/{order_id}")
	public List<OrderDetail> getOrderDetailsByOrderId(@PathVariable("order_id") long id) {
		return orderDetailService.getOrdersByOrderId(id);
	}
}
