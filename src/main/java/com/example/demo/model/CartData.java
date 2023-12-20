package com.example.demo.model;

public class CartData {
    private String id;
    private String name;
    private String price;
    private int count;
    private int total;
    public CartData()
    {
    	
    }
	public CartData(String id, String name, String price, int count, int total) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.count = count;
		this.total = total;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "CartData [id=" + id + ", name=" + name + ", price=" + price + ", count=" + count + ", total=" + total
				+ "]";
	}
	
	

    
}