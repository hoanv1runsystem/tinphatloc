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

import com.vn.backend.dto.CustomerDto;
import com.vn.backend.response.CustomerResponse;
import com.vn.backend.service.CustomerService;
import com.vn.utils.Message;

@Controller
@RequestMapping("/admin")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@RequestMapping("/customer")
	public String view(Model model) {

		Pageable paging = PageRequest.of(0, 20, Sort.by("name").ascending());
		CustomerResponse categoryResponse = customerService.getPaggingCustomer(0, paging);

		model.addAttribute("customers", categoryResponse);
		return "/backend/customer/viewCustomer";

	}

	public String detail(Model model) {
		return "/backend/customer/detailCustomer";

	}

	@RequestMapping(value = "/customer/add", method = RequestMethod.GET)
	public String add(Model model) {
		CustomerDto objectDto = new CustomerDto();
		model.addAttribute("customer", objectDto);

		return "/backend/customer/addCustomer";

	}

	@RequestMapping(value = "/customer/add", method = RequestMethod.POST)
	public String doAdd(CustomerDto dto, Model model, RedirectAttributes redirect) {

//		CustomerResponse resutl = customerService.addValidate(dto);
//		if (Constant.STATUS_SUCCSESS == resutl.getStatus()) {
//			redirect.addFlashAttribute("successMessage", resutl.getMessage());
//			return "redirect:/admin/category/";
//		} else {
//			model.addAttribute("message", resutl.getMessage());
//			model.addAttribute("customer", dto);
//			return "/backend/customer/addCustomer";
//		}

		customerService.add(dto);
		redirect.addFlashAttribute("successMessage", Message.ADD_SUCCESS);
		return "redirect:/admin/customer/";
	}

	@RequestMapping(value = "/customer/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		CustomerDto objectDto = customerService.getDetail(id, 0);

		model.addAttribute("customer", objectDto);
		return "/backend/customer/editCustomer";

	}

	@RequestMapping(value = "/customer/update", method = RequestMethod.POST)
	public String doUpdate(CustomerDto objectDto, Model model, RedirectAttributes redirect) {

		try {
			customerService.update(objectDto);
			redirect.addFlashAttribute("successMessage", "Cập nhật danh mục thành công!");
			return "redirect:/admin/customer/";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/customer/";
	}

	@RequestMapping(value = "/customer/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		try {
			customerService.delete(id);
			redirect.addFlashAttribute("successMessage", "Xóa danh mục thành công!");
			return "redirect:/admin/customer/";
		} catch (Exception e) {
			return "/backend/customer/";
		}
	}

}
