package com.shoppingcart.service.Imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.model.Product;
import com.shoppingcart.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public List<Product> getAllProduct() {
		return productRepository.findAll();

	}

	public void addProduct(Product product) {
		productRepository.save(product);
	}

	public void removeProductById(long id) {
		productRepository.deleteById(id);
	}

	public Optional<Product> getProductById(long id) {
		return productRepository.findById(id);
	}
	
	public List<Product> getProductsByCategoryId(int id){
		return productRepository.findAllByCategory_id(id);
	}
}
