package TphonesShop.controller;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import TphonesShop.model.Brand;
import TphonesShop.model.Contact;
import TphonesShop.model.Order;
import TphonesShop.model.Product;
import TphonesShop.model.User;
import TphonesShop.service.BrandService;
import TphonesShop.service.ContactService;
import TphonesShop.service.OrderDetailService;
import TphonesShop.service.OrderService;
import TphonesShop.service.ProductService;
import TphonesShop.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
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

	@RequestMapping("/")
	public String toHomePage(Model model) {
		model.addAttribute("logError", false);
		model.addAttribute("saleProduct", productService.getSaleProducts());
		model.addAttribute("newest", productService.getNewestProducts());
		model.addAttribute("brandSlideHtml", makeBrandSlideHtml());

		return "user/index.html";
	}

	private String makeBrandSlideHtml() {
		List<Brand> brands = brandService.getBrandList();
		String brand_slide_html = "";

		for (int i = 0; i < brands.size(); i++) {
			if (i % 3 == 0) {
				brand_slide_html += "<div class='item-listcategories'>";
			}
			brand_slide_html += "<div class='list-categories'>" +
					"<div class='desc-listcategoreis'>" +
					"<div class='name-categoreis'>" +
					"<a href='/all-product?brand=" + brands.get(i).getName() + "'>" + brands.get(i).getName() + "</a>" +
					"</div>" +
					"<div class='view-more'>" +
					"<a href='/all-product?brand=" + brands.get(i).getName() + "'>Buy Now</a>" +
					"</div>" +
					"</div>" +
					"<div class='thumb-category'>" +
					"<a href='/all-product?brand=" + brands.get(i).getName() + "'>" +
					"<img src='" + brands.get(i).getImage() + "' alt>" +
					"</a>" +
					"</div>" +
					"</div>";
			if ((i + 1) % 3 == 0) {
				brand_slide_html += "</div>";
			}
		}
		return brand_slide_html;
	}

	@GetMapping("/error")
	public String ErrorPage(HttpServletRequest request) {
		String errorPage = "error"; // default

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				errorPage = "error/404";

			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				errorPage = "error/403";

			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				errorPage = "error/500";

			}
		}

		return errorPage;
	}

	@RequestMapping("/contact")
	public String toContactPage(Model model) {
		model.addAttribute("saveContact", new Contact());
		return "user/contactUs.html";
	}

	@RequestMapping("/about")
	public String toAboutPage(Model model) {
		return "user/about.html";
	}

	@RequestMapping("/cart")
	public String toCartPage(Model model) {
		return "user/cart.html";
	}

	@RequestMapping("/login")
	public String toLoginPage(Model model) {
		return "user/login.html";
	}

	@GetMapping("/account")
	public String toProfilePage(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");

		if (user == null)
			return "redirect:/login";

		model.addAttribute("orders", orderService.getHistoryOrders(user.getId()));

		return "user/profile.html";
	}

	@GetMapping("/order/detail/{id}")
	public String toHistoryCartPage(Model model, @PathVariable("id") long id) {
		model.addAttribute("total", orderService.findOrderById(id).getTotalPrice());
		model.addAttribute("orderDetails", orderDetailService.getOrdersByOrderId(id));
		return "user/history_detail_cart.html";
	}

	@GetMapping("order/delete/{id}")
	public String cancelOrder(@PathVariable("id") long id) {

		Order order = orderService.findOrderById(id);
		order.setStatus(-1);
		orderService.save(order);

		return "redirect:/account";
	}

	@PostMapping("/login")
	public String login(Model model, @Param("username") String username, @Param("password") String password,
			HttpSession httpSession) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		User user = userService.findUserByUsername(username);
		if (user.isStatus() && encoder.matches(password, user.getPassword())) {
			httpSession.setAttribute("user", user);
			if (user.getType().equals("admin")) {
				return "redirect:/adminPage/users";
			} else {
				return toHomePage(model);
			}
		}

		model.addAttribute("logError", true);

		return toLoginPage(model);
	}

	@RequestMapping("/product/{name}")
	public String toShowProductPage(Model model, @PathVariable("name") String name) {
		model.addAttribute("product", productService.findProductByName(name));
		return "user/productDetail.html";
	}

	@RequestMapping("/register")
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
	public String allProducts(
			Model model,
			@RequestParam(value = "minPrice", defaultValue = "0") int minPrice,
			@RequestParam(value = "maxPrice", defaultValue = "-1") int maxPrice,
			@RequestParam(value = "key", required = false) String key,
			@RequestParam(value = "brand", required = false) String[] brands,
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {

		Pageable paging = PageRequest.of(pageNo - 1, 12, Sort.by("id").descending());
		Page<Product> productsPage = null;

		if (brands == null) {
			if (minPrice != 0 || maxPrice != -1) {
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
			if (minPrice != 0 || maxPrice != -1) {
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

}
