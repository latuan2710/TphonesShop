package TphonesShop.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import TphonesShop.model.Admin;
import TphonesShop.model.Brand;
import TphonesShop.model.Product;
import TphonesShop.service.AdminService;
import TphonesShop.service.BrandService;
import TphonesShop.service.OrderService;
import TphonesShop.service.ProductService;
import TphonesShop.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AdminController {

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

	@RequestMapping("/adminPage/admins")
	public String adminPageAdmins(Model model) {
		model.addAttribute("admins", adminService.getAdminList());
		model.addAttribute("logError", false);
		return "/admin/adminPage(admins).html";
	}

	@RequestMapping("/adminPage/users")
	public String adminPageUsers(Model model) {
		model.addAttribute("users", userService.getUserList());
		return "/admin/adminPage(users).html";
	}

	@RequestMapping("/adminPage/products")
	public String adminPageProducts(Model model) {
		model.addAttribute("products", productService.getProductList());
		return "admin/adminPage(products).html";
	}

	@RequestMapping("/adminPage/brands")
	public String adminPageBrands(Model model) {
		model.addAttribute("brands", brandService.getBrandList());
		return "admin/adminPage(brands).html";
	}

	@RequestMapping("/adminPage/orders")
	public String adminPageOrders(Model model) {
		model.addAttribute("orders", orderService.getOrderList());
		return "admin/adminPage(orders).html";
	}

	@RequestMapping("/adminPage/add-product")
	public String toAddProductPage(Model model, Product product) {
		model.addAttribute("brands", brandService.getBrandList());
		model.addAttribute("product", product);
		return "admin/saveProduct.html";
	}

	@RequestMapping("/add-brand")
	public String toSaveBrandPage(Model model, Brand brand) {
		model.addAttribute("brand", brand);
		return "admin/saveBrand.html";
	}

	@RequestMapping("/add-admin")
	public String toSaveAdminPage(Model model, Admin admin) {
		model.addAttribute("saveAdmin", admin);
		return "admin/saveAdmin.html";
	}

	/* SAVE */

	@PostMapping("/saveAdmin/{id}")
	public String saveAdmin(Model model, @ModelAttribute("saveAdmin") Admin admin) {
		adminService.save(admin);
		return adminPageAdmins(model);
	}

	@PostMapping("/saveBrand")
	public String saveBrand(Model model, @RequestParam("brandImg") MultipartFile file,
			@ModelAttribute("brand") Brand brand) {
		try {
			brand = brandService.save(brand);

			String fileName = brand.getId() + ".png";
			String uploadDir = "brand-upload/";

			brand.setImage("/brand-upload/" + fileName);

			saveFile(uploadDir, fileName, file);

			brandService.save(brand);

			System.out.println("Brand added successfully.");

		} catch (Exception e) {
			System.out.println(e);
		}
		return adminPageBrands(model);
	}

	@PostMapping("/saveProduct")
	public String saveProduct(Model model, @RequestParam("productImg") MultipartFile file,
			@RequestParam(value = "sale", required = false) boolean sale, @ModelAttribute("product") Product product) {
		try {
			product = productService.save(product);

			String fileName = product.getId() + ".png";
			String uploadDir = "product-upload/";

			product.setImage("/product-upload/" + fileName);
			product.setSale(sale);
			productService.save(product);

			saveFile(uploadDir, fileName, file);

			System.out.println("Product added successfully.");

		} catch (Exception e) {
			System.out.println(e);
		}
		return adminPageProducts(model);
	}

	/* EDIT */

	@RequestMapping("/editAdmin")
	public String editAdmin(Model model, @Param("id") int id) {
		return toSaveAdminPage(model, adminService.findAdminById(id));
	}

	@RequestMapping("/editUser")
	public String editUser(Model model, @Param("id") int id) {
		UserController userController = new UserController();
		return userController.toRegisterPage(model, userService.findUserById(id));
	}

	@RequestMapping("/editProduct")
	public String editProduct(Model model, Product product, @Param("id") int id) {
		return toAddProductPage(model, productService.findProductById(id));
	}

	@RequestMapping("/editBrand")
	public String editBrand(Model model, Brand brand, @Param("id") int id) {
		return toSaveBrandPage(model, brandService.findBrandById(id));
	}

	/* DELETE */

	@GetMapping("/deleteAdmin/{id}")
	public String deleteAdmin(@PathVariable("id") Long id, Model model) {
		adminService.delete(id);
		return adminPageAdmins(model);
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model) {
		userService.delete(id);
		return adminPageUsers(model);
	}

	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") Long id, Model model) {
		productService.delete(id);
		return adminPageProducts(model);
	}

	@GetMapping("/deleteBrand/{id}")
	public String deleteBrand(@PathVariable("id") Long id, Model model) {

		Brand brand = brandService.findBrandById(id);

		for (Product product : productService.getProductsByBrand(brand.getName())) {
			deleteProduct(product.getId(), model);
		}

		brandService.delete(id);
		return adminPageBrands(model);
	}

	/* SAVE METHOD */

	private void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {

		String orgName = multipartFile.getOriginalFilename();
		if (orgName != "") {
			Path uploadPath = Paths.get(uploadDir);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			try (InputStream inputStream = multipartFile.getInputStream()) {
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ioe) {
				throw new IOException("Could not save image file: " + fileName, ioe);
			}
		}
	}

}
