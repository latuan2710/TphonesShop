package TphonesShop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import TphonesShop.model.Brand;
import TphonesShop.model.Order;
import TphonesShop.model.Product;
import TphonesShop.model.User;
import TphonesShop.service.BrandService;
import TphonesShop.service.OrderService;
import TphonesShop.service.ProductService;
import TphonesShop.service.UserService;
import jakarta.annotation.Resource;
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
	@Autowired
	AdminController adminController;

	@GetMapping("/")
	public String index(Model model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("logUser");
		if (user == null) {
			model.addAttribute("login", false);
		} else {
			model.addAttribute("login", true);
			model.addAttribute("errorLogin", "");
		}

		List<Product> products = productService.getProductList();
		List<Product> hotSale = getHotSale(products);

		model.addAttribute("products", products);
		model.addAttribute("hotSale", hotSale);
		model.addAttribute("brands", brandService.getBrandList());
		return "user/index.html";
	}

	@GetMapping("/loginPage")
	public String toLoginPage() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(Model model, HttpSession httpSession) {
		httpSession.setAttribute("logUser", null);
		return index(model, httpSession);
	}

	@PostMapping("/login/user")
	public String login(@Param("username") String username, @Param("password") String password, Model model,
			HttpSession httpSession) {

		if (username.equals("admin") && password.equals("admin")) {
			System.out.println("admin log ok");
			return adminController.adminPageAdmins(model);
		}

		User user = userService.findUserByUsername(username);
		if (user != null && user.getPassword().equals(password)) {
			httpSession.setAttribute("logUser", user);
			return index(model, httpSession);
		} else {
			model.addAttribute("errorLogin", "Your username or password was wrong");
		}

		return "login.html";
	}

	@GetMapping("/registerPage")
	public String toRegisterPage(Model model) {
		List<String> usernames = new ArrayList<>();
		for (User user : userService.getUserList()) {
			usernames.add(user.getUsername());
		}
		model.addAttribute("usernames", usernames);
		model.addAttribute("user", new User());
		return "register.html";
	}

	@PostMapping("/register")
	public String register(Model model, @ModelAttribute("user") User user) {
		userService.save(user);
		return "login.html";
	}

	public List<Product> getHotSale(List<Product> products) {
		List<Product> hotSale = new ArrayList<>();
		products.sort((p1, p2) -> Double.compare(p1.getCost(), p2.getCost()));

		for (int i = 0; i < products.size(); i++) {
			if (i == 8)
				break;
			hotSale.add(products.get(i));
		}
		return hotSale;
	}
}
