package com.ogabe.mall.product.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ogabe.mall.product.entity.Product;
import com.ogabe.mall.product.service.ProductService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.HashedMap;

import com.ogabe.mall.product.service.ProductService;
import com.ogabe.mall.util.OgabeConstants;

@RestController
@RequestMapping(path = {OgabeConstants.PRODUCT_API})
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@Autowired
	ServletContext context; 
	
	@Autowired
	private HttpServletRequest request;

	@GetMapping
	public Map<String, Object> getAll(@RequestParam(required=false) String category, @RequestParam(required=false) String productName){
		
		List<Product> products = null;
		Map<String, Object> respObject = new HashedMap<String, Object>();
		try {
			products = service.getAll(category, productName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (products != null) {
			respObject.put("msg","success");
			respObject.put("products", products);
		}
		return respObject;
	}
	
	@PutMapping
	public void save(@RequestBody Product product){
		try {
			service.save(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@PostMapping(value = "/uploadImage")
	public Map<String, String> imageUploaded() {
		Map<String, String> respObject = new HashedMap<String, String>();
		final String saveDirectory = "/images_uploaded";
		String realpath = context.getRealPath(saveDirectory);
		File fSaveDirectory = new File(realpath);
		if (!fSaveDirectory.exists()) {
			fSaveDirectory.mkdir();
		}
		String imagePath = "";
		Collection<Part> parts;
		try {
			parts = request.getParts();
			System.out.println("LocalName: "+request.getLocalAddr());
			for (Part part : parts) {
				SimpleDateFormat sDateFormat = new SimpleDateFormat("yyMMddHHmmss");
				String filename = sDateFormat.format(new Date()) + "_" + part.getSubmittedFileName();
				File f = new File(fSaveDirectory, filename);
				part.write(f.toString());
				imagePath = "http://localhost:8080/yokult"+ saveDirectory + "/" + filename;
				System.out.println("image path: " + imagePath);
			}
			respObject.put("msg", "success");
			respObject.put("proPicture", imagePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respObject;
	}
	
	@PostMapping
	public Map<String, String> addProduct(@RequestBody Product product) {
		Integer status = service.add(product);
		Map<String, String> respObject = new HashedMap<String, String>();
		if (status > 0) {
			respObject.put("msg","success");
		} else {
			respObject.put("msg","fail");
		}
		return respObject;
	}
}
