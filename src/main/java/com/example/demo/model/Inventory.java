	package com.example.demo.model;
	
	import java.sql.Timestamp;
import java.util.Date;
	
	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	
	@Entity
	public class Inventory {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String name;
	    private String size;
	    private String color;
	    private int quantity;
	    private double price;
	    private Date dateLastOrder;
	    private Date dateLastShip;
	    public Inventory() {
	    	Date currentDate = new Date();
	        Timestamp currentTimestamp = new Timestamp(currentDate.getTime());

	        this.dateLastOrder = currentTimestamp;
	    	this.dateLastShip = currentTimestamp; 
	    };
		public Inventory(String name, String size, String color, int quantity, double price,Date dateLastOrder,
				Date dateLastShip) {
			this.name = name;
			this.size = size;
			this.color = color;
			this.price = price;
			this.quantity = quantity;
			this.dateLastOrder = dateLastOrder;
			this.dateLastShip = dateLastShip;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		public Date getDateLastOrder() {
			return dateLastOrder;
		}
		public void setDateLastOrder(Date dateLastOrder) {
			this.dateLastOrder = dateLastOrder;
		}
		public Date getDateLastShip() {
			return dateLastShip;
		}
		public void setDateLastShip(Date dateLastShip) {
			this.dateLastShip = dateLastShip;
		}
		
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
	    
		
	
	}
