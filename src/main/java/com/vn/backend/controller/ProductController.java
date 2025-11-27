package com.vn.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vn.backend.dto.ProductDto;
import com.vn.backend.response.ProductResponse;
import com.vn.backend.service.CategoryService;
import com.vn.backend.service.ProductService;
import com.vn.utils.Constant;
import com.vn.utils.Message;

@Controller
@RequestMapping("/admin")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/product")
	public String view(Model model) {

		Pageable paging = PageRequest.of(Constant.DEFAULT_PAGE, Constant.DEFAULT_PAGE_SIZE,
				Sort.by("name").ascending());
		ProductResponse products = productService.getPaggingProduct(Constant.DELETE_FLAG_ACTIVE, paging);
		model.addAttribute("products", products);

		return "/backend/product/viewProduct";
	}

	@GetMapping("/product/detail/{id}")
	public String detail(@PathVariable("id") Long id, Model model) {
		ProductDto productDto = productService.getDetail(id, Constant.DELETE_FLAG_ACTIVE);
		model.addAttribute("product", productDto);
		return "/backend/product/detailProduct";
	}

	@RequestMapping(value = "/product/add", method = RequestMethod.GET)
	public String add(Model model) {
		ProductDto productDto = new ProductDto();
		productDto.setCategories(categoryService.getListAll(Constant.DELETE_FLAG_ACTIVE));
		model.addAttribute("product", productDto);

		return "/backend/product/addProduct";

	}

	@RequestMapping(value = "/product/add", method = RequestMethod.POST)
	public String doAdd(ProductDto productDto, Model model, RedirectAttributes redirect) {

		ProductResponse resutl = productService.addValidate(productDto);
		if (Constant.STATUS_SUCCSESS == resutl.getStatus()) {
			redirect.addFlashAttribute("successMessage", resutl.getMessage());
			return "redirect:/admin/product/";
		} else {
			model.addAttribute("message", resutl.getMessage());
			productDto.setCategories(categoryService.getListAll(Constant.DELETE_FLAG_ACTIVE));
			model.addAttribute("product", productDto);
			return "/backend/product/addProduct";
		}

	}

	@RequestMapping(value = "/product/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		ProductDto productDto = productService.getDetail(id, 0);
		productDto.setCategories(categoryService.getListAll(Constant.DELETE_FLAG_ACTIVE));
		model.addAttribute("product", productDto);
		return "/backend/product/updateProduct";

	}

	@RequestMapping(value = "/product/update", method = RequestMethod.POST)
	public String doUpdate(ProductDto productDto, Model model, RedirectAttributes redirect) throws Exception {
		ProductResponse resutl = productService.update(productDto);
		if (Constant.STATUS_SUCCSESS == resutl.getStatus()) {
			redirect.addFlashAttribute("successMessage", resutl.getMessage());
			return "redirect:/admin/product/";
		} else {
			model.addAttribute("message", resutl.getMessage());
			productDto.setCategories(categoryService.getListAll(Constant.DELETE_FLAG_ACTIVE));
			model.addAttribute("product", productDto);
			return "/backend/product/updateProduct";
		}

	}

	@RequestMapping(value = "/product/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		try {
			productService.delete(id);
			redirect.addFlashAttribute("successMessage", Message.DELETE_SUCCESS);
			return "redirect:/admin/product/";
		} catch (Exception e) {
			return "redirect:/admin/product/";
		}
	}

}
