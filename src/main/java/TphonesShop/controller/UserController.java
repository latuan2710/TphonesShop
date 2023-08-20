package TphonesShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import TphonesShop.model.Contact;
import TphonesShop.model.User;
import TphonesShop.service.BrandService;
import TphonesShop.service.ContactService;
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
	BrandService brandService;
	@Resource
	OrderService orderService;
	@Resource
	ProductService productService;
	@Resource
	UserService userService;
	@Resource
	ContactService contactService;
	@Autowired
	AdminController adminController;

	@RequestMapping("/")
	public String toHomePage(Model model) {
		model.addAttribute("logError", false);
		return "user/index.html";
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

	@PostMapping("/login")
	public String login(Model model, @Param("username") String username, @Param("password") String password,
			HttpSession httpSession) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		User user = userService.findUserByUsername(username);
		if (user.isStatus() && encoder.matches(password, user.getPassword())) {
			httpSession.setAttribute("user", user);
			if (user.getType().equals("admin")) {
				return adminController.adminPageUsers(model);
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
		return "user/showProduct.html";
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

}
