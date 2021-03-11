package com.shoppingcart.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.shoppingcart.dto.ProductDTO;
import com.shoppingcart.model.Category;
import com.shoppingcart.service.Imp.CategoryService;
import com.shoppingcart.service.Imp.ProductService;

@Controller
public class AdminController {

	@Autowired
	CategoryService categorySerive;
	@Autowired
	ProductService productSerive;

	@GetMapping("/admin")
	public String adminHome() {
		return "adminHome";
	}

	@GetMapping("/admin/categories")
	public String getCat(Model model) {
		// model.addAllAttributes("categories", categorySerive.getAllCategory());
		model.addAttribute("categories", categorySerive.getAllCategory());
		return "categories";
	}

	@GetMapping("/admin/categories/add")
	public String getCartAdd(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";

	}

	@PostMapping("/admin/categories/add")
	public String postCartAdd(@ModelAttribute("category") Category category) {
		categorySerive.addCategory(category);
		return "redirect:/admin/categories";

	}

	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCart(@PathVariable int id) {
		categorySerive.removeCategoryById(id);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/update/{id}")
	public String updateCart(@PathVariable int id, Model model) {
		Optional<Category> category = categorySerive.getCategoryById(id);
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "categoriesAdd";
		} else
			return "404";
	}

	// Product Section

	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("products", productSerive.getAllProduct());
		return "products";

	}
	
	@GetMapping("/admin/products/add")
	public String productGetAdd(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categorySerive.getAllCategory());
		return "productsAdd";

	}


}
