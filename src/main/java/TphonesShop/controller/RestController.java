package TphonesShop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import TphonesShop.model.Admin;
import TphonesShop.model.User;
import TphonesShop.service.AdminService;
import TphonesShop.service.BrandService;
import TphonesShop.service.OrderService;
import TphonesShop.service.ProductService;
import TphonesShop.service.UserService;
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
	@Resource
	AdminService adminService;

	@PostMapping("/checkLogin")
	public boolean checkPassLog(@RequestParam("password") String password, @RequestParam("username") String name) {

		User user = userService.findUserByUsername(name);
		if (user == null) {
			Admin admin = adminService.findAdminByUsername(name);

			if (admin == null) {
				return false;
			} else {
				if (admin.getPassword().equals(password)) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			if (user.getPassword().equals(password)) {
				return true;
			} else {
				return false;
			}
		}

	}

	@PostMapping("/checkUser")
	public boolean checkUser(@RequestParam("username") String name) {
		System.out.println(name);
		for (String username : userService.getListName()) {
			if (username.equals(name)) {
				return true;
			}
		}
		return false;
	}

	@PostMapping("/checkAdmin")
	public boolean checkAdmin(@RequestParam("username") String name) {
		for (String username : adminService.getListName()) {
			if (username.equals(name)) {
				return true;
			}
		}
		return false;
	}
}
