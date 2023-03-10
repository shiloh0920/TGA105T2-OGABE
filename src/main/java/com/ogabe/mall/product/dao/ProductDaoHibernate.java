package com.ogabe.mall.product.dao;

import java.util.List;

import jakarta.persistence.PersistenceContext;
//import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ogabe.mall.product.entity.Product;



@Repository
public class ProductDaoHibernate implements ProductDao {
	@PersistenceContext
	private Session session;

	public Session getSession() {
		return this.session;
	}

	@Override
	public Integer insert(Product product) {
		Integer pk = (Integer) this.getSession().save(product);
		if (pk > 0) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public Integer update(Product product) {
		Product oldProduct = this.getSession().get(Product.class, product.getProdID());
		System.out.println("oldProduct" + oldProduct);
		if (oldProduct != null) {
			
			oldProduct.setProdName(product.getProdName());
			oldProduct.setProdStock(product.getProdStock());
			oldProduct.setProdPrice(product.getProdPrice());
			oldProduct.setProdSpecs(product.getProdSpecs());
			oldProduct.setProdCategory(product.getProdCategory());
				return 1;
		}
		return 0;
	}

	@Override
	public List<Product> selectAll(String category, String productName) {
//		String hql2 = "from Product where category :cate and productName = :name";
		String hql = "from Product";
		if (category != null || productName != null) {
			hql += " where";
			if (category != null) {
				hql += " proCategory = :cate";
			}
			if (productName != null) {
				if (category == null) {
					hql += " proName like :name";
				} else {					
					hql += " and proName like :name";
				}
			}
		}
		System.out.println("hql= " + hql);
		Query<Product> query = this.getSession().createQuery(hql, Product.class);
		if (category != null) {
			query.setParameter("cate", category);
		}
		if (productName != null) {
			query.setParameter("name", "%" + productName + "%");
		}
		List<Product> list = query.list();

		return list;
	}
}
