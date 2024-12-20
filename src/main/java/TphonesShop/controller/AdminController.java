package TphonesShop.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import TphonesShop.service.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import TphonesShop.model.Brand;
import TphonesShop.model.Contact;
import TphonesShop.model.Order;
import TphonesShop.model.Product;
import TphonesShop.model.User;
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
    @Resource
    CloudinaryService cloudinaryService;

    @GetMapping("/adminPage/admins")
    public String adminPageAdmins(Model model) {
        model.addAttribute("users", userService.getAdminList());
        return "admin/adminPage(admins).html";
    }

    @GetMapping("/adminPage/users")
    public String adminPageUsers(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "admin/adminPage(users).html";
    }

    @GetMapping("/adminPage/products")
    public String adminPageProducts(Model model) {
        model.addAttribute("products", productService.getAll());
        return "admin/adminPage(products).html";
    }

    @GetMapping("/adminPage/brands")
    public String adminPageBrands(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("brands", brandService.getBrandList());
        return "admin/adminPage(brands).html";
    }

    @GetMapping("/adminPage/orders")
    public String adminPageOrders(Model model) {
        model.addAttribute("orders", orderService.getOrdersBuyed());
        return "admin/adminPage(orders).html";
    }

    @GetMapping("/adminPage/contacts")
    public String adminPageContacts(Model model) {
        model.addAttribute("contacts", contactService.getContactList());
        return "admin/adminPage(contacts).html";
    }

    /* ADD */

    @GetMapping("/add-user")
    public String toSaveUserPage(Model model, User user) {
        model.addAttribute("saveUser", user);
        return "admin/saveUser.html";
    }

    @GetMapping("/add-product")
    public String toAddProductPage(Model model, Product product) {
        model.addAttribute("brands", brandService.getBrandList());
        model.addAttribute("product", product);
        return "admin/saveProduct.html";
    }

    @GetMapping("/add-brand")
    public String toSaveBrandPage(Model model, Brand brand) {
        model.addAttribute("brand", brand);
        return "admin/saveBrand.html";
    }

    /* SAVE */

    @PostMapping("/saveUser/{id}")
    public String saveUser(Model model, @ModelAttribute("saveUser") User user) {
        try {
            if (user.getId() == 0) {
                ShaEncoder encoder = new ShaEncoder();
                user.setPassword(encoder.encode(user.getPassword()));
            }
            userService.save(user);
            model.addAttribute("alert", "success");
        } catch (Exception e) {
            model.addAttribute("alert", "error");
        }
        return adminPageAdmins(model);
    }

    @PostMapping("/saveBrand")
    public String saveBrand(Model model,
                            @RequestParam("brandImg") MultipartFile file,
                            @ModelAttribute Brand brand) {
        try {
            if (brand.getId() != 0)
                brand = brandService.findBrandById(brand.getId());

            if (file != null) {
                String brandImage = brand.getImage();
                if (brandImage != null) {
                    cloudinaryService.deleteFile(brandImage, "brands");
                }

                brandImage = saveFile(file, "brands");
                brand.setImage(brandImage);
            }

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
                              @ModelAttribute Product product) {
        try {
            if (product.getId() != 0)
                product = productService.findProductById(product.getId());

            if (file != null) {
                String productImage = product.getFeaturedImage();
                if (productImage != null) {
                    cloudinaryService.deleteFile(productImage, "products");
                }

                productImage = saveFile(file, "products");
                product.setFeaturedImage(productImage);
            }

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

    @GetMapping("/editUser")
    public String editUser(Model model, @Param("id") int id) {
        return toSaveUserPage(model, userService.findUserById(id));
    }

    @GetMapping("/editProduct")
    public String editProduct(Model model, @Param("id") int id) {
        return toAddProductPage(model, productService.findProductById(id));
    }

    @GetMapping("/editBrand")
    public String editBrand(Model model, @Param("id") int id) {
        return toSaveBrandPage(model, brandService.findBrandById(id));
    }

    /* DELETE */

    @GetMapping("/disable/{id}")
    public String disableUser(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        try {
            user.setStatus(!user.isStatus());
            userService.save(user);

            model.addAttribute("alert", "success");
        } catch (Exception e) {
            model.addAttribute("alert", "error");
        }

        if (user.getType().equals("admin"))
            return adminPageAdmins(model);

        return adminPageUsers(model);
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        try {
            Product product = productService.findProductById(id);

            if (product.getOrderDetails().isEmpty()) {
                cloudinaryService.deleteFile(product.getFeaturedImage(), "products");
                productService.delete(id);
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
    public String deleteBrand(@PathVariable Long id, Model model) throws IOException {

        try {
            Brand brand = brandService.findBrandById(id);

            if (brand.getProducts().isEmpty()) {
                cloudinaryService.deleteFile(brand.getImage(), "brands");
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

    @GetMapping("/order/{order_id}/status/{order_status}")
    public String updateOrderStatus(
            @PathVariable int order_id,
            @PathVariable int order_status) {

        Order order = orderService.findOrderById(order_id);
        order.setStatus(order_status);
        orderService.save(order);

        return "redirect:/adminPage/orders";
    }

    @GetMapping("/processContact/{id}")
    public String contactProcess(@PathVariable long id, Model model) {

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

    private String saveFile(MultipartFile multipartFile, String folderName) throws IOException {
        return cloudinaryService
                .uploadFile(multipartFile, folderName).get("url").toString();
    }

}
