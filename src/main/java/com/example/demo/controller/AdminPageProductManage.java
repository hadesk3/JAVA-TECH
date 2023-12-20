package com.example.demo.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.ProductService;

@Controller
public class AdminPageProductManage {
	@Autowired
	ProductService productService;
	@GetMapping("/admin-productManager")
	public String get(Model model,HttpServletRequest request)
	{
		
		List<Product> l = productService.getAllProducts();
		model.addAttribute("products", l);
		return "admin-productManager";
	}
	 @GetMapping("/admin/product/add")
	    public String showAddProductForm(Model model) {
	        model.addAttribute("product", new Product());
	        return "admin-add-product";
	    }

	    @PostMapping("/admin/productManager/add")
	    public String addProduct(@ModelAttribute("product") Product product, @RequestParam("avatar") MultipartFile avatar) throws IOException {
	    	
	    	LocalDateTime now = LocalDateTime.now();
	        
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	        String formattedDateTime = now.format(formatter);
        	String nameImage = formattedDateTime+".jpg";
            byte[] imageData = avatar.getBytes();
            String filePath = "./src/main/resources/static/uploads/" +nameImage;
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(imageData);
            fos.close();
            product.setImage("./uploads/"+nameImage);	    	
	    	
	        productService.addProduct(product);
	        return "redirect:/admin-productManager";
	    }
	 @GetMapping("admin/product/edit/{id}")
	    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
	        Optional<Product> product = productService.getProductById(id);
	        if (product.isPresent()) {
	            model.addAttribute("product", product.get());
	            return "admin-edit-product";
	        } else {
	            return "redirect:/admin-productManager";
	        }
	    }

	    @PostMapping("admin/product/edit/{id}")
	    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product) {
	        Optional<Product> existingProduct = productService.getProductById(id);
	        if (existingProduct.isPresent()) {
	            product.setId(id);
	            product.setImage(existingProduct.get().getImage());
	            productService.updateProduct(product);
	        }
	        return "redirect:/admin-productManager";
	    }
	    
	    @PostMapping("admin/product/delete/{id}")
	    public String deleteProduct(@PathVariable("id") Long id) 
	    {
	        Optional<Product> existingProduct = productService.getProductById(id);
	        if (existingProduct.isPresent()) 
	        {
	            Product p = existingProduct.get();
	            if (p.getPurchased().equals("no")) {
	                productService.deleteProduct(id);
	            }}
	        return "redirect:/admin-productManager";
	    }
	  
	    
}
