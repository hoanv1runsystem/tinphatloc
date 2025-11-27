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

import com.vn.backend.dto.ContactDto;
import com.vn.backend.response.ContactResponse;
import com.vn.backend.service.ContactService;
import com.vn.utils.Constant;

@Controller
@RequestMapping("/admin")
public class ContactController {

	@Autowired
	private ContactService contactService;

	@RequestMapping("/contact")
	public String view(Model model) {

		Pageable paging = PageRequest.of(Constant.DEFAULT_PAGE, Constant.DEFAULT_PAGE_SIZE,
				Sort.by("name").ascending());
		ContactResponse contacts = contactService.getPaggingContact(Constant.DELETE_FLAG_ACTIVE, paging);
		model.addAttribute("contacts", contacts);

		return "/backend/contact/viewContact";
	}

	@RequestMapping(value = "/contact/add", method = RequestMethod.GET)
	public String add(Model model) {
		ContactDto contactDto = new ContactDto();
		model.addAttribute("contactDto", contactDto);

		return "/backend/contact/addContact";

	}

	@RequestMapping(value = "/contact/add", method = RequestMethod.POST)
	public String doAdd(ContactDto contactDto, Model model, RedirectAttributes redirect) {
		contactService.add(contactDto);
		redirect.addFlashAttribute("successMessage", "Thêm mới liên hệ thành công thành công!");
		return "redirect:/admin/contact/";

	}

	@RequestMapping(value = "/contact/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		ContactDto contactDto = contactService.getDetail(id, 0);

		model.addAttribute("contactDto", contactDto);
		return "/backend/contact/editContact";

	}

	@RequestMapping(value = "/contact/update", method = RequestMethod.POST)
	public String doUpdate(ContactDto contactDto, Model model, RedirectAttributes redirect) {

		try {
			contactService.update(contactDto);
			redirect.addFlashAttribute("successMessage", "Cập nhật liên hệ thành công thành công!");
			return "redirect:/admin/contact/";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/contact/";
	}

	@RequestMapping(value = "/contact/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		try {
			contactService.delete(id);
			redirect.addFlashAttribute("successMessage", "Xóa liên hệ thành công!");
			return "redirect:/admin/contact/";
		} catch (Exception e) {
			return "/backend/contact/editContact";
		}
	}
}
