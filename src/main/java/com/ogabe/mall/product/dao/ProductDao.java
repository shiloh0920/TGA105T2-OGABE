package com.ogabe.mall.product.dao;

import java.util.List;

import com.ogabe.mall.product.entity.Product;

public interface ProductDao {
	Integer insert(Product product);

//	ProductDao selectByProductIdAndProduct(ProductDaoImpl product);

	Integer update(com.ogabe.mall.product.entity.Product product);

//	Integer delete(ProductDao product);

	List<Product> selectAll(String category, String productName);

//	Integer insert(Product product);

}
