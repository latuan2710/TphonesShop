package TphonesShop.controller;

import java.util.HashMap;
import java.util.List;

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

	@PostMapping("/buy-now")
	public boolean BuyNow(HttpSession httpSession,
			@RequestParam("product_id") long product_id,
			@RequestParam(value = "quantity", defaultValue = "1") int quantity) {

		User user = (User) httpSession.getAttribute("user");

		if (user != null) {

			Product product = productService.findProductById(product_id);

			Order order = orderService.save(new Order(user.getUsername(), true));

			orderDetailService
					.save(new OrderDetail(order.getId(), product_id, product.getName(), quantity,
							product.getFinal_price()));

			product.setQuantity(product.getQuantity() - quantity);
			productService.save(product);

			return true;
		}

		return false;
	}

	@PostMapping("/buy-all")
	public boolean BuyAll(HttpSession httpSession) {

		User user = (User) httpSession.getAttribute("user");

		if (user != null) {
			Order order = orderService.getCart(user.getUsername());

			// Update quantity of product
			List<OrderDetail> orderDetails = orderDetailService.getOrdersByOrderId(order.getId());
			for (OrderDetail orderDetail : orderDetails) {
				Product product = productService.findProductById(orderDetail.getProductId());
				product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
				productService.save(product);
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

			Order order = orderService.getCart(user.getUsername());

			for (HashMap<String, Integer> orderHashMap : data) {
				OrderDetail orderDetail = orderDetailService.getOrdersByProductId(
						order.getId(), orderHashMap.get("productId"));
				orderDetail.setQuantity(orderHashMap.get("quantity"));
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

			Order order = orderService.getCart(user.getUsername());
			Product product = productService.findProductById(product_id);

			if (order == null) {
				order = orderService.save(new Order(user.getUsername(), false));
			}

			OrderDetail orderDetail = orderDetailService.getOrdersByProductId(order.getId(), product_id);

			if (orderDetail != null) {
				orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
				orderDetail.setFinalPrice();
			} else {
				orderDetail = new OrderDetail(order.getId(), product_id, product.getName(), quantity,
						product.getFinal_price());
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
			Order order = orderService.getCart(user.getUsername());
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
			Order order = orderService.getCart(user.getUsername());

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
}
