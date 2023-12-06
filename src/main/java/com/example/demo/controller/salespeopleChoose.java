package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

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
import com.example.demo.model.Purchase;
import com.example.demo.model.User;
import com.example.demo.reposity.CustomerRepo;
import com.example.demo.reposity.OrderRepo;
import com.example.demo.reposity.PurchaseRepo;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

@Controller
public class salespeopleChoose {
	@Autowired
		ProductService productService;

    @Autowired
    private CustomerRepo customerRepository;

    @Autowired
    private OrderRepo orderRepository;
    
    @Autowired
    private PurchaseRepo purchaseRepo;
    @Autowired
    private UserService userService;
	@GetMapping("/page-buy-product")
	public String g(Model model)
	{
		List<Product> l = productService.getAllProducts();
		model.addAttribute("products", l);
		return "product-index";
	}
	@PostMapping("/submit-cart")
	public String get(@RequestBody CartData[]  cartData, Principal prin,  HttpSession session)
	{
		
		String username = prin.getName();
		User u = userService.findbyUsername(username);
		String userId = u.getId() + "";
		List<CartData> l = new ArrayList<>();
		 for (CartData item : cartData) {
	        	l.add(item);
	        }
		  Map<String, List<CartData>> post = new HashMap<>();
		  post.put(userId, l);
		  
		  
		  session.setAttribute("cart", post);
		  
		return "product-index"; 
	
	}
	@GetMapping("/checkout")
	public String checkout(HttpSession session,Principal princ) {
		
		  User u = userService.findbyUsername(princ.getName()); // Replace with the desired userId
		  	String userId = u.getId() + "";
		    // Retrieve the map from the session using the userId as the key
		    Map<String, List<CartData>> userMap = (Map<String, List<CartData>>) session.getAttribute("cart");
		    // Check if the userMap exists
		    if (userMap != null) {
		        List<CartData> cartDataList = userMap.get(userId);
		        for (CartData cartData : cartDataList) {
		            System.out.println("Product Name: " + cartData.getName());
		            // Print other properties of CartData as needed
		            // ...
		        }
		    }
		    else
		    {
		    	System.out.println("ko có gì");
		    }

	    return "checkout";
	}
	
	
	

}
