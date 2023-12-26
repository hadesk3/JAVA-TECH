package com.example.demo.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private Long id_inventory;
    private String barcode;
    private String productName;
    private double importPrice;
    private double retailPrice;
    private String category;
    private Date creationDate;
    private String image;
    private String purchased;
    
  

    public Product() {
        this.purchased = "no";
        Date currentDate = new Date();
        Timestamp currentTimestamp = new Timestamp(currentDate.getTime());

        this.creationDate = currentTimestamp;
    }


	// Getters and Setters
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
		public Long getId_inventory() {
		return id_inventory;
	}


	public void setId_inventory(Long id_inventory) {
		this.id_inventory = id_inventory;
	}


		public Product(Long id,Long idInvent, String barcode, String productName, double importPrice, double retailPrice, String category,
			Date creationDate, String image) {
			this.id_inventory = idInvent;
		this.id = id;
		this.barcode = barcode;
		this.productName = productName;
		this.importPrice = importPrice;
		this.retailPrice = retailPrice;
		this.category = category;
		this.creationDate = creationDate;
		this.image = image;
		this.purchased = "no";
	}


	public String getPurchased() {
		return purchased;
	}


	public void setPurchased(String purchased) {
		this.purchased = purchased;
	}
	
    
}