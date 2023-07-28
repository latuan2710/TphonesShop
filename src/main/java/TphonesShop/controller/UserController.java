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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import TphonesShop.model.Admin;
import TphonesShop.model.Brand;
import TphonesShop.model.Order;
import TphonesShop.model.Product;
import TphonesShop.model.User;
import TphonesShop.service.AdminService;
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
	@Resource
	AdminService adminService;
	@Autowired
	AdminController adminController;
	@Autowired
	HttpSession httpSession;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("brands", brandService.getBrandList());
		model.addAttribute("SamSung", productService.getProductsByBrand("SamSung"));
		model.addAttribute("Apple", productService.getProductsByBrand("Apple"));
		model.addAttribute("Xiaomi", productService.getProductsByBrand("Xiaomi"));
		model.addAttribute("logError", false);
		return "user/index.html";
	}

	@GetMapping("/login")
	public String toLoginPage(Model model) {
		return "user/login.html";
	}

	@PostMapping("/login")
	public String login(Model model, @Param("username") String username, @Param("password") String password)
			throws Exception {

		for (Admin admin : adminService.getAdminList()) {
			if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
				httpSession.setAttribute("admin", admin);
				return adminController.adminPageAdmins(model);
			}
		}

		for (User user : userService.getUserList()) {
			if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
				httpSession.setAttribute("user", user);
				return index(model);
			}
		}

		model.addAttribute("logError", true);

		return toLoginPage(model);
	}

	@GetMapping("/logout")
	public String logout(Model model) throws Exception {
		httpSession.removeAttribute("user");
		httpSession.removeAttribute("admin");
		return index(model);
	}

	@GetMapping("/register")
	public String toRegisterPage(Model model, User user) {
		model.addAttribute("saveUser", user);
		return "user/register.html";
	}

	@PostMapping("/saveUser/{id}")
	public String saveUser(Model model, @ModelAttribute("saveUser") User user) {
		userService.save(user);

		if (httpSession.getAttribute("admin") != null) {
			return adminController.adminPageUsers(model);
		}
		return toLoginPage(model);
	}
}
