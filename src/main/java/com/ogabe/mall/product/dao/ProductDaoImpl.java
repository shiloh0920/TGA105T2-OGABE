package com.ogabe.mall.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ogabe.mall.product.entity.Product;



public class ProductDaoImpl implements ProductDao {

	private DataSource datasource;

	public ProductDaoImpl() throws NamingException {
		datasource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/Yokult");
	}

//	final String SELECTALL = "Select PROID,PRONAME,PROSTOCK,PROPRICE,PROSPECS,PROBRAND,PROPICTURE,PROCATEGORY from PRODUCT";
	final String SELECTALL = "Select * from product";

	public List<Product> selectAll(String category, String productName) {

		try {
			Connection conn = datasource.getConnection();

			String query = SELECTALL;
			if (category != null || productName != null) {
				query += " Where ";
			}

			if (category != null) {
				query += " prodcategory = '" + category + "'";
			}

			if (productName != null) {
				if (category != null) {
					query += " AND ";
				}
				query += "  prodname like '%" + productName + "%'";
			}
			
			query += " ORDER BY proid DESC";

			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			try (ResultSet rs = ps.executeQuery()) {
				List<Product> products = new ArrayList<Product>();
				System.out.println("Show product list:");
				while (rs.next()) {
					Product p = new Product();
					p.setProdID(rs.getInt("proid"));
					p.setProdName(rs.getString("prodname"));
					p.setProdStock(rs.getInt("prodstock"));
					p.setProdPrice(rs.getInt("prodprice"));
					p.setProdSpecs(rs.getString("prodspecs"));
					p.setProdImage(rs.getString("prodimage"));
					p.setProdCategory(rs.getString("prodcategory"));
					products.add(p);
					System.out.println(p);
				}
				return products;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
@Override
	public Integer insert(Product product) {
		String insertStr = "INSERT INTO `product` (`prodname`, `prodstock`, `prodprice`, `prodspecs`, `prodimage`, `prodcategory`) VALUES (?,?,?,?,?,?);";
		try {
			Connection conn = datasource.getConnection();
			PreparedStatement ps = conn.prepareStatement(insertStr);
			ps.setString(1, product.getProdName());
			ps.setInt(2, product.getProdStock());
			ps.setInt(3, product.getProdPrice());
			ps.setString(4, product.getProdSpecs());
			ps.setString(6, product.getProdImage());
			ps.setString(7, product.getProdCategory());
			return ps.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	} 

	@Override
	public Integer update(Product product) {
		String updateStr = """
					UPDATE product SET
						proddname = ?,
						proddstock = ?,
						prodprice = ?,
						prodspecs = ?,
						prodcategory = ?
					WHERE prodid = ?
				""";
		try {
			Connection conn = datasource.getConnection();
			PreparedStatement ps = conn.prepareStatement(updateStr);
			ps.setString(1, product.getProdName());
			ps.setInt(2, product.getProdStock());
			ps.setInt(3, product.getProdPrice());
			ps.setString(4, product.getProdSpecs());
			ps.setString(6, product.getProdCategory());
			ps.setInt(7, product.getProdID());
			
			return ps.executeUpdate();
		
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}

//	public Integer delete(Product product) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public Integer insert(ProductDao product) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public ProductDao selectByProductIdAndProduct(ProductDaoImpl product) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Integer delete(ProductDao product) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
