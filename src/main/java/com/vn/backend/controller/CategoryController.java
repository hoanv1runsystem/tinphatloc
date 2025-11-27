package com.vn.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vn.backend.dto.CategoryDto;
import com.vn.backend.response.CategoryResponse;
import com.vn.backend.service.CategoryService;
import com.vn.utils.Constant;

@Controller
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping("/category")
	public String view(Model model) {

		Pageable paging = PageRequest.of(0, 20, Sort.by("name").ascending());
		CategoryResponse categoryResponse = categoryService.getPaggingCategory(0, paging);

		model.addAttribute("categories", categoryResponse);
		return "/backend/category/viewCategory";

	}

	public String detail(Model model) {
		return "/backend/category/detailCategory";

	}

	@RequestMapping(value = "/category/add", method = RequestMethod.GET)
	public String add(Model model) {
		CategoryDto categoryDto = new CategoryDto();
		model.addAttribute("categoryDto", categoryDto);

		return "/backend/category/addCategory";

	}

	@RequestMapping(value = "/category/add", method = RequestMethod.POST)
	public String doAdd(CategoryDto categoryDto, Model model, RedirectAttributes redirect) {

		CategoryResponse resutl = categoryService.addCategory(categoryDto);
		if (Constant.STATUS_SUCCSESS == resutl.getStatus()) {
			redirect.addFlashAttribute("successMessage", resutl.getMessage());
			return "redirect:/admin/category/";
		} else {
			model.addAttribute("message", resutl.getMessage());
			model.addAttribute("categoryDto", categoryDto);
			return "/backend/category/addCategory";
		}

	}

	@RequestMapping(value = "/category/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		CategoryDto categoryDto = categoryService.getDetail(id, 0);

		model.addAttribute("categoryDto", categoryDto);
		return "/backend/category/editCategory";

	}

	@RequestMapping(value = "/category/update", method = RequestMethod.POST)
	public String doUpdate(CategoryDto categoryDto, Model model, RedirectAttributes redirect) throws Exception {
		categoryService.update(categoryDto);
		redirect.addFlashAttribute("successMessage", "Cập nhật danh mục thành công!");
		return "redirect:/admin/category/";

	}

	@RequestMapping(value = "/category/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		try {
			categoryService.delete(id);
			redirect.addFlashAttribute("successMessage", "Xóa danh mục thành công!");
			return "redirect:/admin/category/";
		} catch (Exception e) {
			return "/backend/category/editCategory";
		}
	}

}
