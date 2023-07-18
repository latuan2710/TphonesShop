package TphonesShop.controller;

import java.io.IOException;

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

import TphonesShop.model.Order;
import TphonesShop.model.Product;
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

	@RequestMapping("/add-product")
	public String toAddProductPage(Model model, Product product) {
		model.addAttribute("brands", brandService.getBrandList());
		model.addAttribute("product", product);
		System.out.println(product);
		return "admin/addProduct.html";
	}

	@RequestMapping("/editProduct")
	public String editProduct(Model model, Product product, @Param("id") int id) {
		return toAddProductPage(model, productService.findProductById(id));
	}

	@PostMapping("submitProduct")
	public String addProduct(Model model, @RequestParam("productImg") MultipartFile file,
			@ModelAttribute("product") Product product,@Param("id") int id) {
		System.out.println(product);
		try {
			byte[] imageData = file.getBytes();
			product.setImageData(imageData);
			product.setId(id);
			productService.save(product);
			System.out.println("Product added successfully.");

		} catch (Exception e) {
			System.out.println(e);
		}
		return adminPageProducts(model);
	}

	@GetMapping("/image/display/{id}")
	public void showImage(@PathVariable("id") Long id, HttpServletResponse response, Product product)
			throws ServletException, IOException {
		product = productService.findProductById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(product.getImage());
		response.getOutputStream().close();
	}

	@GetMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") Long id, Model model) {

		Product product = productService.findProductById(id);

		for (Order order : orderService.getOrdersByProduct(product.getName())) {
			orderService.delete(order.getId());
		}

		productService.delete(id);
		return adminPageProducts(model);
	}
}
