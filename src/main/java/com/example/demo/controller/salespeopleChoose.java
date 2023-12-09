package com.example.demo.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.CartData;
import com.example.demo.model.Customer;
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
	public String checkout(HttpSession session, Principal princ, Model model) {
	   int total = 0;
	   int quantity=0;
		User user = userService.findbyUsername(princ.getName());
	    String userId = user.getId() + "";

	    Object attribute = session.getAttribute("cart");

	    if (attribute instanceof List) {
	        List<Map<String, List<CartData>>> values = (List<Map<String, List<CartData>>>) attribute;

	        for (Map<String, List<CartData>> map : values) {
	            if (map.containsKey(userId)) {
	                List<CartData> cartDataList = map.get(userId);

	                for ( CartData map2 : cartDataList) {
	                	quantity += map2.getCount();
						total += map2.getTotal();
					}
	                
	                session.removeAttribute("cart");
	                break; 
	            }
	        }
	    } else if (attribute instanceof Map) {
	        Map<String, List<CartData>> map = (Map<String, List<CartData>>) attribute;

	        if (map.containsKey(userId)) {
	            List<CartData> cartDataList = map.get(userId);
	            for ( CartData map2 : cartDataList) {
                	quantity += map2.getCount();
					total += map2.getTotal();
				}
	        }
	    } else {
	        System.out.println("Không có gì trong session");
	    }
	    	
	    model.addAttribute("Total", total);
	    model.addAttribute("Quantity", quantity);
	    

	    return "checkout";
	}
	
	@PostMapping("/check-phone")
	public ResponseEntity<?> checkout(@RequestParam("phoneNumber") String phoneNumber) {
	    Customer customer = customerRepository.findByPhoneNumber(phoneNumber);

	    if (customer != null) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("found", true);
	        response.put("fullName", customer.getName());
	        response.put("address", customer.getAddress());
	        return ResponseEntity.ok(response);
	    } else {
	        Map<String, Object> response = new HashMap<>();
	        response.put("found", false);
	        return ResponseEntity.ok(response);
	    }
	}
	
	@PostMapping("/get-customer-infor")
		
	public String getCusomer(@RequestParam("name")String name,@RequestParam("address")String address ,@RequestParam("phoneNumber")String phone)
	{
		Customer c = new Customer();
		c.setAddress(address);
		c.setName(name);
		c.setPhoneNumber(phone);
		customerRepository.save(c);
		return "redirect:/checkout";
	}
	
	@GetMapping("/process-payment-part1")
	public String  p(@RequestParam("total")String total) {
		
		return "";
	}
	

}
