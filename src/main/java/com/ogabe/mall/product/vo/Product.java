package com.ogabe.mall.product.vo;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

@Entity
@DynamicInsert
@Table(name = "product")
public class Product{
	@Id
	@Column(name = "prodid") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer prodID; // 商品編號
	
	@Column(name = "prodname")
	private String prodName; // 商品名稱
	
	@Column(name = "prodstock")
	private Integer prodStock; // 商品庫存
	
	@Column(name = "prodprice")
	private Integer prodPrice; // 商品價格

	@Column(name = "prodspecs")
	private String prodSpecs; // 商品內容規格
	
	@Column(name = "prodimage")
	private String prodImage; // 商品圖片
	
	@Column(name = "prodcategory")
	private String prodCategory; // 商品種類
	
	@Override
	public String toString() {
		return "Product [prodID=" + prodID + ", prodName=" + prodName + ", prodStock=" + prodStock + ", prodPrice=" + prodPrice
				+ ", prodSpecs=" + prodSpecs + ", prodImage=" + prodImage + ", prodCategory=" + prodCategory + "]";
	}

	public Integer getProdID() {
		return prodID;
	}

	public void setProdID(Integer prodID) {
		this.prodID = prodID;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Integer getProdStock() {
		return prodStock;
	}

	public void setProdStock(Integer prodStock) {
		this.prodStock = prodStock;
	}

	public Integer getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(Integer prodPrice) {
		this.prodPrice = prodPrice;
	}

	public String getProdSpecs() {
		return prodSpecs;
	}

	public void setProdSpecs(String prodSpecs) {
		this.prodSpecs = prodSpecs;
	}

	public String getProdImage() {
		return prodImage;
	}

	public void setProdImage(String prodImage) {
		this.prodImage = prodImage;
	}

	public String getProdCategory() {
		return prodCategory;
	}

	public void setProdCategory(String prodCategory) {
		this.prodCategory = prodCategory;
	}

	public Product(Integer prodID, String prodName, Integer prodStock, Integer prodPrice, String prodSpecs, String prodBrand,
			String prodImage, String prodCategory) {
		super();
		this.prodID = prodID;
		this.prodName = prodName;
		this.prodStock = prodStock;
		this.prodPrice = prodPrice;
		this.prodSpecs = prodSpecs;
		this.prodImage = prodImage;
		this.prodCategory = prodCategory;
	}

	public Product() {
		super();
	}

		
}



