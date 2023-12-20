package com.example.demo.controller;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Order;
import com.example.demo.model.Profit;
import com.example.demo.model.Purchase;
import com.example.demo.reposity.OrderRepo;
import com.example.demo.reposity.ProfitRepo;
import com.example.demo.reposity.PurchaseRepo;

@Controller
public class analysis {
	@Autowired
	OrderRepo orderRepo;
	@Autowired
	PurchaseRepo purchaseRepo;
	@Autowired
	ProfitRepo profitRepo;
	  @GetMapping("/graph")
	    public String viewSales(Model model, @RequestParam String startDate, @RequestParam String endDate) {
		  LocalDate localDate1 = LocalDate.parse(startDate);
		  LocalDate localDate2 = LocalDate.parse(endDate);
		  List<Purchase> purchase = purchaseRepo.findByPurchaseDateBetween(localDate1, localDate2);
		  
		  int totalAmount = 0;
		  for (Purchase purchase2 : purchase) {
			  
			totalAmount += purchase2.getTotalAmount().intValue();
		}
		 List<Profit> profit = profitRepo.findByPurchaseDateBetween(localDate1, localDate2);
		 int profitAmount = 0;
		 for (Profit profit2 : profit) {
			profitAmount += profit2.getProfit();
		}
		  
		  List<Order> orders = orderRepo.findByPurchaseDateBetween(localDate1, localDate2);
	        if(orders != null)
	        {
	        	int totalOrder = orders.size();
	        	int totalQuantity = 0;
	        	for (Order order : orders) {
					totalQuantity += order.getQuantity();
				}
	            model.addAttribute("orders", orders);
	            
	  		  
	  		  int[] data = new int[2];
	  		  data[0] = totalAmount;
	  		  data[1] = profitAmount;
	  		  System.out.println( "profit"+profitAmount);
	  		  int[] data2 = new int[2];

	  		  data2[0] = totalOrder;
	  		  data2[1] = totalQuantity;
	  		  System.out.println(totalOrder + " " + totalQuantity);

	  	        model.addAttribute("totals", data);
	  	        model.addAttribute("totals2", data2);

		        return "graph";
	        }
	        else
	        {
	        	return "error";
	        }
	    
	    }
	  @PostMapping("/graph")
	  public String createOrder(
	          @RequestParam("startDate") String startDate,
	          @RequestParam("endDate") String endDate,
	          Model model,
	          RedirectAttributes redirectAttributes) {

	      redirectAttributes.addFlashAttribute("successMessage", "Order created successfully!");

	      return "redirect:/graph?startDate=" + startDate + "&endDate=" + endDate;
	  }
	  @GetMapping("/analysis")
	  public String cdd()
	  {
		  return "graph";
	  }
}
