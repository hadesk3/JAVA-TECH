package com.example.demo.model;


import java.util.ArrayList;
import java.util.List;

public class Invoice {
	 private String invoiceNumber;
	    private String customerName;
	    private String date;
	    private List<InvoiceItem> items;
	    private String quantity;
	    private String total;
	    private String staff;

	    // Constructors, getters, and setters
	    public Invoice()
	    {
	    	
	    }
	    // Constructor without items
	    public Invoice(String invoiceNumber, String customerName, String date) {
	        this.invoiceNumber = invoiceNumber;
	        this.customerName = customerName;
	        this.date = date;
	        this.items = new ArrayList<>();
	    }

	    // Add an item to the invoice
	    public void addItem(InvoiceItem item) {
	        items.add(item);
	    }

		public String getInvoiceNumber() {
			return invoiceNumber;
		}

		public void setInvoiceNumber(String invoiceNumber) {
			this.invoiceNumber = invoiceNumber;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public List<InvoiceItem> getItems() {
			return items;
		}

		public void setItems(List<InvoiceItem> items) {
			this.items = items;
		}
		public String getQuantity() {
			return quantity;
		}
		public void setQuantity(String quantity) {
			this.quantity = quantity;
		}
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
		public String getStaff() {
			return staff;
		}
		public void setStaff(String staff) {
			this.staff = staff;
		}
	    


   }
