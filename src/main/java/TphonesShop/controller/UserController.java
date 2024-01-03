package TphonesShop.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import TphonesShop.model.*;
import TphonesShop.service.*;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

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
	@Resource
	ContactService contactService;
	@Resource
	TokenService tokenService;
	@Autowired
	JavaMailSender mailSender;

	@GetMapping("/")
	public String toHomePage(Model model) {
		Pageable paging = PageRequest.of(0, 10, Sort.by("id").descending());

		model.addAttribute("saleProduct", productService.getSaleProducts());
		model.addAttribute("brands", brandService.getBrandList());
		model.addAttribute("iphones", productService.findByBrand(new String[] { "Apple" }, paging));
		model.addAttribute("samsungs", productService.findByBrand(new String[] { "SamSung" }, paging));
		model.addAttribute("xiaomis", productService.findByBrand(new String[] { "Xiaomi" }, paging));
		return "user/index.html";
	}

	@GetMapping("/contact")
	public String toContactPage(Model model) {
		model.addAttribute("saveContact", new Contact());
		return "user/contactUs.html";
	}

	@GetMapping("/about")
	public String toAboutPage(Model model) {
		return "user/about.html";
	}

	@GetMapping("/cart")
	public String toCartPage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null)
			return "redirect:/login";
		Order order = orderService.getCart(user.getId());

		model.addAttribute("order", order);
		return "user/cart.html";
	}

	@GetMapping("/login")
	public String toLoginPage(Model model) {
		return "user/login.html";
	}

	@PostMapping("/login")
	public String login(Model model, @Param("username") String username, @Param("password") String password,
			HttpSession httpSession) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		User user = userService.findUserByUsername(username);

		if (!user.isStatus()) {
			model.addAttribute("flag", false);
			model.addAttribute("message", "Your account has been disabled!");

			return toLoginPage(model);
		}

		if (encoder.matches(password, user.getPassword())) {

			httpSession.setAttribute("user", user);

			if (user.getType().equals("admin")) {
				return "redirect:/adminPage/admins";
			} else {
				return "redirect:/";
			}
		}

		model.addAttribute("flag", false);
		model.addAttribute("message", "Incorrect Password!");

		return toLoginPage(model);
	}

	@GetMapping("/account")
	public String toProfilePage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user == null)
			return "redirect:/login";

		return "user/profile.html";
	}

	@GetMapping("/history")
	public String toHistoryPage(Model model, HttpSession session,
			@RequestParam(defaultValue = "1") int pageNo) {
		User user = (User) session.getAttribute("user");
		Page<Order> orders = new PageImpl<>(new ArrayList<>());
		Pageable paging = PageRequest.of(pageNo - 1, 5);

		if (user != null) {
			orders = orderService.getHistoryOrders(paging, user.getId());
		}

		model.addAttribute("page", pageNo);
		model.addAttribute("orders", orders);
		model.addAttribute("total", orders.getTotalPages());
		return "user/history_order.html";
	}

	@GetMapping("/order/detail/{id}")
	public String toHistoryCartPage(Model model, @PathVariable long id) {
		model.addAttribute("total", orderService.findOrderById(id).getTotalPrice());
		model.addAttribute("orderDetails", orderDetailService.getOrdersByOrderId(id));
		return "user/history_detail_cart.html";
	}

	@GetMapping("order/delete/{id}")
	public String cancelOrder(@PathVariable long id) {

		Order order = orderService.findOrderById(id);
		order.setStatus(-1);
		orderService.save(order);

		return "redirect:/account";
	}

	@GetMapping("/product/{name}")
	public String toShowProductPage(Model model, @PathVariable String name) {
		model.addAttribute("product", productService.findProductByName(name));
		return "user/productDetail.html";
	}

	@GetMapping("/register")
	public String toRegisterPage(Model model, User user) {
		model.addAttribute("saveUser", user);
		return "user/register.html";
	}

	@PostMapping("/register")
	public String registerNewUser(Model model, @ModelAttribute("saveUser") User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setType("user");
		userService.save(user);
		return toLoginPage(model);
	}

	@GetMapping("/logout")
	public String logout(Model model, HttpSession httpSession) throws Exception {
		httpSession.removeAttribute("user");
		return "redirect:/";
	}

	@PostMapping("/forgot-password")
	public String forgotPass(@RequestParam String email, HttpServletRequest request, Model model) {

		String tokenString = RandomStringUtils.randomAlphanumeric(60);

		User user = userService.findUserByEmail(email);

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expirationTime = now.plusMinutes(30);

		tokenService.save(new Token(tokenString, user.getId(), expirationTime));

		String siteURL = request.getRequestURL().toString();
		siteURL = siteURL.replace(request.getServletPath(), "");

		String resetPasswordLink = siteURL + "/reset-password?token=" + tokenString;
		sendEmail(email, resetPasswordLink);

		model.addAttribute("flag", true);
		model.addAttribute("message", "Please check your email to get the password change link!");

		return toLoginPage(model);
	}

	@GetMapping("/change-password")
	public String changePassword(HttpSession session, HttpServletRequest request, Model model) {

		String tokenString = RandomStringUtils.randomAlphanumeric(60);

		User user = (User) session.getAttribute("user");

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime expirationTime = now.plusMinutes(30);

		tokenService.save(new Token(tokenString, user.getId(), expirationTime));

		return toResetPasswordPage(tokenString, model);
	}

	@GetMapping("/reset-password")
	public String toResetPasswordPage(@RequestParam("token") String tokenString, Model model) {
		Token token = tokenService.findByToken(tokenString);
		if (token == null)
			model.addAttribute("tokenExprired", true);

		model.addAttribute("token", tokenString);
		return "user/recover_password.html";
	}

	@PostMapping("/reset-password")
	public String resetPassword(Model model, @RequestParam("token") String tokenString,
			@RequestParam String password, HttpSession httpSession) {

		Token token = tokenService.findByToken(tokenString);
		if (token == null) {
			model.addAttribute("flag", false);
			model.addAttribute("message", "Your token link is invalid!");
			return toLoginPage(model);
		}
		User user = userService.findUserById(token.getUserId());

		model.addAttribute("flag", true);
		model.addAttribute("message", "You have successfully changed your password.");

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));

		tokenService.deleteByUserId(user.getId());
		userService.save(user);

		httpSession.removeAttribute("user");

		return toLoginPage(model);
	}

	public void sendEmail(String recipientEmail, String link) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(recipientEmail);
			helper.setFrom("contact@shopme.com", "Tphones Shop Support");

			String subject = "Here's the link to reset your password";
			String content = "<p>Hello,</p>"
					+ "<p>You have requested to reset your password.</p>"
					+ "<p>Click the link below to reset your password:</p>"
					+ "<p><a href=\"" + link + "\">" + link + "</a></p>"
					+ "<br>"
					+ "<p>Ignore this email if you do remember your password, "
					+ "or you have not made the request.</p>";

			helper.setSubject(subject);
			helper.setText(content, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/submit_contact_form")
	public String saveContact(Model model, @ModelAttribute("saveContact") Contact contact) {
		try {
			contactService.save(contact);
			model.addAttribute("alert", "true");
		} catch (Exception e) {
			model.addAttribute("alert", "false");
		}
		return toContactPage(model);
	}

	public String toProductsPage(Model model, int page, int[] totalPage, Page<Product> products) {
		model.addAttribute("brands", brandService.getBrandList());
		model.addAttribute("products", products);
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		return "user/products.html";
	}

	@GetMapping("/all-product")
	public String allProducts(Model model,
			@RequestParam(defaultValue = "0") int minPrice,
			@RequestParam(defaultValue = "0") int maxPrice,
			@RequestParam(required = false) String key,
			@RequestParam(value = "brand", required = false) String[] brands,
			@RequestParam(defaultValue = "1") int pageNo) {

		Pageable paging = PageRequest.of(pageNo - 1, 12, Sort.by("id").descending());
		Page<Product> productsPage = null;

		if (brands == null) {
			if (maxPrice != 0) {
				if (key == null) {
					productsPage = productService.findByPrice(paging, minPrice, maxPrice);
				} else {
					productsPage = productService.findByPriceAndKey(paging, minPrice, maxPrice, key);
				}
			} else if (key == null) {
				productsPage = productService.findAllInPage(paging);
			} else {
				productsPage = productService.findByKey(key, paging);
			}
		} else {
			if (maxPrice != 0) {
				if (key == null) {
					productsPage = productService.findByPriceAndBrand(paging, minPrice, maxPrice, brands);
				} else {
					productsPage = productService.findByPriceAndKeyAndBrand(paging, minPrice, maxPrice, key, brands);
				}
			} else if (key == null) {
				productsPage = productService.findByBrand(brands, paging);
			} else {
				productsPage = productService.findByBrandAndKey(brands, paging, key);
			}
		}

		int total = productsPage.getTotalPages();

		return toProductsPage(model, pageNo, IntStream.rangeClosed(1, total).toArray(), productsPage);
	}

	@GetMapping("/shipping/{order_id}")
	public String toShippingPage(Model model, @PathVariable("order_id") long id) {
		model.addAttribute("order", orderService.findOrderById(id));
		return "user/shipping.html";
	}

	@PostMapping("/buy-now")
	public String buyNow(
			Model model,
			HttpSession session,
			@RequestParam("product_id") long id,
			@RequestParam(value = "order_quantity", defaultValue = "1") int quantity) {

		User user = (User) session.getAttribute("user");
		if (user == null)
			return "redirect:/login";

		Product product = productService.findProductById(id);
		Order order = orderService.save(new Order(user, 5));
		OrderDetail orderDetail = new OrderDetail(order, product, quantity, product.getFinal_price());

		order.setTotalPrice(orderDetail.getFinalPrice());
		order.addOrderDetails(orderDetail);

		orderDetailService.save(orderDetail);

		return toShippingPage(model, order.getId());
	}

}
