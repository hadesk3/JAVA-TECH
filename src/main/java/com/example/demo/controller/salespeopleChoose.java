package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.CartData;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

@Controller
public class salespeopleChoose {
	@Autowired
		ProductService productService;
	@GetMapping("/page-buy-product")
	public String g(Model model)
	{
		List<Product> l = productService.getAllProducts();
		model.addAttribute("products", l);
		return "product-index";
	}
	@PostMapping("/submit-cart")
	public String get(@RequestBody CartData[]  cartData)
	{
		 String cartDataString = Arrays.toString(cartData);
	        System.out.println("Dữ liệu giỏ hàng: " + cartDataString);
		return "redirect:/page-buy-product";
	}

}
