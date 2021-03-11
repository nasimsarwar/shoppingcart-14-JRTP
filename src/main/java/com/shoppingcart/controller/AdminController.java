package com.shoppingcart.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import com.shoppingcart.dto.ProductDTO;
import com.shoppingcart.model.Category;
import com.shoppingcart.model.Product;
import com.shoppingcart.service.Imp.CategoryService;
import com.shoppingcart.service.Imp.ProductService;

@Controller
public class AdminController {
   public static String uploadDri = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
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
	
	@PostMapping("/admin/products/add")
	public String productAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,
			                     @RequestParam("productImage")MultipartFile file,
			                     @RequestParam("imgName")String imgName) throws IOException{
		
		Product product = new Product();
		/*
		 * BeanUtils.copyProperties(productDTO, product);
		 * productSerive.addProduct(product);
		 */
		
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categorySerive.getCategoryById(productDTO.getCategoryId()).get());
        
        String imageUUID;
       if(!file.isEmpty()) {
    	   
    	   imageUUID = file.getOriginalFilename();
    	   Path fileNameAndPath = Paths.get(uploadDri, imageUUID);
    	   Files.write(fileNameAndPath, file.getBytes());
       }
       else 
    	   imageUUID = imgName;
    	  
       
       product.setImageName(imageUUID);
       productSerive.addProduct(product);
       System.out.println(uploadDri);
		return "redirect:/admin/products";
		
		
	}
	
	


}
