package com.example.demo.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.VNPAY.Config;
import com.example.demo.model.CartData;
import com.example.demo.model.Customer;
import com.example.demo.model.Invoice;
import com.example.demo.model.InvoiceItem;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.Purchase;
import com.example.demo.model.User;
import com.example.demo.reposity.CustomerRepo;
import com.example.demo.reposity.OrderRepo;
import com.example.demo.reposity.ProductRepo;
import com.example.demo.reposity.PurchaseRepo;
import com.example.demo.service.UserService;
import com.itextpdf.io.IOException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;


@Controller
public class PaymentController {

	@Autowired
	private UserService userService;
	@Autowired
	private PurchaseRepo purchaseRepo;
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private ProductRepo productRepo; 
	
	@GetMapping("/process-payment")
	public String submitOrder(HttpServletResponse response,@RequestParam("total") String total, HttpSession session,HttpServletRequest request, Principal princ) throws IOException, FileNotFoundException {
		if(total != null)
		{
			String phoneNumber = "";
			int quantity=0;
			int total_2 = 0;
			List<CartData> list_cart_data = new ArrayList<>();
			Cookie[] cookies = request.getCookies();

	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if ("phone".equals(cookie.getName())) {
	                     phoneNumber = cookie.getValue();
	                     cookie.setMaxAge(-1);
	                     response.addCookie(cookie);
	                    System.out.println("Phone Number: " + phoneNumber);
	                }
	            }
	        }
	        
			
				System.out.println(total);
				User user = userService.findbyUsername(princ.getName());
			    String userId = user.getId() + "";
			    Object attribute = session.getAttribute("cart");

		    if (attribute instanceof List) {
		        List<Map<String, List<CartData>>> values = (List<Map<String, List<CartData>>>) attribute;

		        for (Map<String, List<CartData>> map : values) {
		            if (map.containsKey(userId)) {
		                List<CartData> cartDataList = map.get(userId);

		                
		                for ( CartData map2 : cartDataList) {
		                	list_cart_data.add(map2);
		                	quantity += map2.getCount();
							total_2 += map2.getTotal();
						}
		                session.removeAttribute("cart");                
		                session.removeAttribute("phone");
		                System.out.println(list_cart_data.size());
		                break; 
		            }
		        }
		    } else if (attribute instanceof Map) {
		        Map<String, List<CartData>> map = (Map<String, List<CartData>>) attribute;

		        if (map.containsKey(userId)) {
		            List<CartData> cartDataList = map.get(userId);
		            for ( CartData map2 : cartDataList) {
	                	list_cart_data.add(map2);
	                	quantity += map2.getCount();
						total_2 += map2.getTotal();

					}
		        }
		    } else {
		        System.out.println("Không có gì trong session");
		    }
			
		    Customer customer = customerRepo.findByPhoneNumber(phoneNumber);
		    if(customer != null)
		    {
		    	 Purchase purchase = new Purchase();
				    purchase.setCustomer(customer);
			        BigDecimal bigDecimalValue = new BigDecimal(total_2);
				    purchase.setTotalAmount(bigDecimalValue);
			        LocalDate currentDate = LocalDate.now();
			        purchase.setPurchaseDate(currentDate);
					purchaseRepo.save(purchase);
					
					List<Product> list_prList = new ArrayList<>();
					List<InvoiceItem> invoiceItem = new ArrayList<>();
					for(int i = 0; i < list_cart_data.size(); i++)
					{
						Product p = productRepo.findByProductName(list_cart_data.get(i).getName());
						InvoiceItem inv= new InvoiceItem();
						inv.setName(p.getProductName());
						inv.setPrice(p.getRetailPrice());
						inv.setQuantity(list_cart_data.get(i).getCount());
						invoiceItem.add(inv);
						list_prList.add(p);
					}
					Order order = new Order();
					order.setCustomer(customer);
					order.setQuantity(quantity);
					order.setProducts(list_prList);
					order.setPurchaseDate(currentDate);
					orderRepo.save(order);	
					long countRows = purchaseRepo.count();
		            String filePath = "./src/main/resources/static/uploads/" +"invoice "+ countRows+".pdf";

		            
					Invoice invoice = new Invoice();
					invoice.setCustomerName(customer.getName());
					invoice.setDate(currentDate+"");
					invoice.setInvoiceNumber("0000" + countRows);
					invoice.setItems(invoiceItem);
					invoice.setQuantity(quantity+"");
					invoice.setTotal(total_2 +"");
					invoice.setStaff(princ.getName());
					createInvoicePdf(filePath, invoice);
		    }
		   
		    return "redirect:/payment?price=" + total;
		    

		}
		else
		{
			return "error";

		}
	}
	
	@GetMapping("/payment")
	public RedirectView getPay(@RequestParam(required = false) String price) throws UnsupportedEncodingException{
		
		String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = Long.parseLong(price);
        amount = amount*10000;
        String bankCode = "NCB";
        
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = Config.vnp_TmnCode;
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        
        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(paymentUrl);

        return redirectView;
	}
	


    @GetMapping("/vnpay")
    public String handleVnpayCallback(
    		) {
        // Handle the VNPAY callback logic here
       
            return "pay-success"; // Return the error view for other response codes or transaction statuses
        
    }
    
    
    private static void createInvoicePdf(String filePath, Invoice invoice) throws IOException, FileNotFoundException {
       
        // Create PdfDocument and PdfWriter
        PdfDocument pdf = new PdfDocument(new PdfWriter(new File(filePath)));

        // Create Document
        Document document = new Document(pdf);

        // Add content to the PDF
        document.add(new Paragraph("Invoice"));
        document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNumber()));
        document.add(new Paragraph("Customer: " + invoice.getCustomerName()));
        document.add(new Paragraph("Date: " + invoice.getDate()));

        // Add items to the PDF
        document.add(new Paragraph("Items:"));
        for (InvoiceItem item : invoice.getItems()) {
            document.add(new Paragraph(
                    String.format("  %s - Quantity: %d, Price: $%.2f", item.getName(), item.getQuantity(), item.getPrice())
            ));
        }
        
        document.add(new Paragraph("============================="));
        document.add(new Paragraph("Total quantity: "+ invoice.getQuantity()));
        document.add(new Paragraph("Total money: "+ invoice.getTotal()));
        document.add(new Paragraph("Person in charge: "+ invoice.getStaff()));


        // Close the document
        document.close();
    }

	   }