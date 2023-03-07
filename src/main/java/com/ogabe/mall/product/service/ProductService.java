package com.ogabe.mall.product.service;

import java.util.List;

import com.ogabe.mall.product.entity.Product;

public interface ProductService {

	List<Product>  getAll(String category, String productName);
	
	void save(Product product);
	
	Integer add(Product product);
}