package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Product;
import com.example.demo.reposity.InventoryRepo;

@Controller
public class Inventory {
		@Autowired
		InventoryRepo invenRepo;
		
		@GetMapping("admin/inventory")
		public String inven(Model model)
		{
			List<com.example.demo.model.Inventory> l = invenRepo.findAll();
			model.addAttribute("productList", l);
			return "inventory";
		}
		
		  @GetMapping("/admin/add-inventory")
		    public String showAddProductForm(Model model) {
		        model.addAttribute("product", new com.example.demo.model.Inventory());

		        return "admin-add-inventory";
		    }
		  
		  @PostMapping("/admin/add-inventory-save")
		  	
		  public String a(@ModelAttribute("product") com.example.demo.model.Inventory product)
		  {
			  invenRepo.save(product);
			  return "redirect:/admin/inventory";
		  }
		  
		  
		  @GetMapping("/admin/edit-inventory/{id}")
		    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
		        Optional<com.example.demo.model.Inventory> product = invenRepo.findById(id);
		        if (product.isPresent()) {
		            model.addAttribute("product", product.get());
		            return "admin-edit-inventory";
		        } else {
		            return "redirect:/admin/inventory";
		        }
		    }

		    @PostMapping("admin/inventory/edit/{id}")
		    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") com.example.demo.model.Inventory product) {
		        Optional<com.example.demo.model.Inventory> existingProduct = invenRepo.findById(id);
		        if (existingProduct.isPresent()) {
		            product.setId(id);
		            product.setName(existingProduct.get().getName());
		            Date currentDate = new Date();
			        Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
		            product.setDateLastOrder(currentTimestamp);
		            product.setDateLastShip(currentTimestamp);
		            invenRepo.save(product);
		        }
		        return "redirect:/admin/inventory";
		    }
		 
}
