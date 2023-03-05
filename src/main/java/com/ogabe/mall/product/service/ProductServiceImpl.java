package com.ogabe.mall.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ogabe.mallsp.product.dao.ProductDao;
import com.ogabe.mallsp.product.vo.Product;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao dao;

	public ProductServiceImpl() {
	}

	@Override
	public List<Product> getAll(String category, String productName) {
		return dao.selectAll(category,productName);
	}

	@Override
	public void save(Product product) {
		dao.update(product);
	}

	@Override
	public Integer add(Product product) {
		return dao.insert(product);
	}
}
