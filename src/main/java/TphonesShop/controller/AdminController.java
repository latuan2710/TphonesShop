package TphonesShop.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import TphonesShop.model.Brand;
import TphonesShop.model.Contact;
import TphonesShop.model.OrderDetail;
import TphonesShop.model.Product;
import TphonesShop.model.User;
import TphonesShop.service.BrandService;
import TphonesShop.service.ContactService;
import TphonesShop.service.OrderDetailService;
import TphonesShop.service.OrderService;
import TphonesShop.service.ProductService;
import TphonesShop.service.UserService;
import jakarta.annotation.Resource;

@Controller
public class AdminController {

	@Resource
	UserService userService;
	@Resource
	BrandService brandService;
	@Resource
	ProductService productService;
	@Resource
	OrderService orderService;
	@Resource
	OrderDetailService orderDetailService;
	@Resource
	ContactService contactService;

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
		model.addAttribute("brand", new Brand());
		model.addAttribute("brands", brandService.getBrandList());
		return "admin/adminPage(brands).html";
	}

	@RequestMapping("/adminPage/orders")
	public String adminPageOrders(Model model) {
		model.addAttribute("orders", orderDetailService.getOrderList());
		return "admin/adminPage(orders).html";
	}

	@RequestMapping("/adminPage/contacts")
	public String adminPageContacts(Model model) {
		model.addAttribute("contacts", contactService.getContactList());
		return "admin/adminPage(contacts).html";
	}

	/* ADD */

	@RequestMapping("/add-user")
	public String toSaveUserPage(Model model, User user) {
		model.addAttribute("saveUser", user);
		return "admin/saveUser.html";
	}

	@RequestMapping("/add-product")
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

	/* SAVE */

	@PostMapping("/saveUser/{id}")
	public String saveUser(Model model, @ModelAttribute("saveUser") User user) {
		try {
			if (user.getId() == 0) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				user.setPassword(encoder.encode(user.getPassword()));
			}
			userService.save(user);
			model.addAttribute("alert", "success");
		} catch (Exception e) {
			model.addAttribute("alert", "error");
		}
		return adminPageUsers(model);
	}

	@PostMapping("/saveBrand")
	public String saveBrand(Model model,
			@RequestParam("brandImg") MultipartFile file,
			@ModelAttribute("brand") Brand brand) {
		try {
			brand = brandService.save(brand);

			String uploadDir = "brand-upload/" + brand.getId() + "/";

			String featuredImg = brand.getId() + ".jpg";

			brand.setImage("/" + uploadDir + featuredImg);

			saveFile(uploadDir, featuredImg, file);

			brandService.save(brand);

			System.out.println("Brand added successfully.");
			model.addAttribute("alert", "success");
		} catch (Exception e) {
			model.addAttribute("alert", "error");
		}
		return adminPageBrands(model);
	}

	@PostMapping("/saveProduct")
	public String saveProduct(Model model,
			@RequestParam("product_img") MultipartFile file,
			@ModelAttribute("product") Product product) {
		try {
			product = productService.save(product);

			String uploadDir = "product-upload/" + product.getId() + "/";

			String fileName = product.getId() + ".jpg";
			product.setFeaturedImage("/" + uploadDir + fileName);
			saveFile(uploadDir, fileName, file);

			product.setFinal_price();
			productService.save(product);

			System.out.println("Product saved successfully.");
			model.addAttribute("alert", "success");
		} catch (Exception e) {
			model.addAttribute("alert", "error");
		}
		return adminPageProducts(model);
	}

	/* EDIT */

	@RequestMapping("/editUser")
	public String editUser(Model model, @Param("id") int id) {
		return toSaveUserPage(model, userService.findUserById(id));
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

	@GetMapping("/disable/{id}")
	public String disableUser(@PathVariable("id") Long id, Model model) {
		try {
			User user = userService.findUserById(id);
			user.setStatus(!user.isStatus());
			userService.save(user);

			model.addAttribute("alert", "success");
		} catch (Exception e) {
			model.addAttribute("alert", "error");
		}
		return adminPageUsers(model);
	}

	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") Long id, Model model) {
		try {
			List<OrderDetail> orderDetails = orderDetailService.getOrdersByProuctId(id);

			if (orderDetails.isEmpty()) {
				productService.delete(id);
				deleteFile("product-upload/" + id + "/");
				model.addAttribute("alert", "success");
			} else {
				model.addAttribute("alert", "warning");
			}

		} catch (Exception e) {
			model.addAttribute("alert", "error");
		}
		return adminPageProducts(model);
	}

	@GetMapping("/deleteBrand/{id}")
	public String deleteBrand(@PathVariable("id") Long id, Model model) throws IOException {

		try {
			Brand brand = brandService.findBrandById(id);

			List<String> brands = new ArrayList<>();
			brands.add(brand.getName());

			List<Product> products = productService.getProductsByBrand(brands);

			if (products.isEmpty()) {
				// deleteFile(brand.getImage());
				deleteFile("brand-upload/" + id + "/");
				brandService.delete(id);
				model.addAttribute("alert", "success");
			} else {
				model.addAttribute("alert", "warning");
			}
		} catch (Exception e) {
			model.addAttribute("alert", "error");
		}
		return adminPageBrands(model);
	}

	@GetMapping("/processContact/{id}")
	public String contactProcess(@PathVariable("id") long id, Model model) {

		try {
			Contact contact = contactService.findContactById(id);
			contact.setStatus(!contact.getStatus());
			contactService.save(contact);
			model.addAttribute("alert", "success");
		} catch (Exception e) {
			model.addAttribute("alert", "error");
		}

		return adminPageContacts(model);
	}

	/* SAVE IMAGE METHOD */

	private void saveFile(String uploadDir,
			String fileName,
			MultipartFile multipartFile) throws IOException {

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

	/* DELETE IMAGE METHOD */

	private void deleteFile(String imgPath) throws IOException {
		// String[] part = imgPath.split("/");
		// imgPath = part[1] + "/" + part[2] + "/";
		File file = new File(imgPath);
		deleteDirectory(file);
		file.delete();
	}

	private void deleteDirectory(File file) {
		for (File subfile : file.listFiles()) {
			if (subfile.isDirectory()) {
				deleteDirectory(subfile);
			}
			subfile.delete();
		}
	}

}
