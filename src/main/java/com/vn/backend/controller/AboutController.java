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

import com.vn.backend.dto.AboutDto;
import com.vn.backend.response.AboutResponse;
import com.vn.backend.service.AboutService;
import com.vn.utils.Constant;
import com.vn.utils.Message;

@Controller
@RequestMapping("/admin")
public class AboutController {

	@Autowired
	private AboutService aboutService;

	@RequestMapping("/about")
	public String view(Model model) {

		Pageable paging = PageRequest.of(Constant.DEFAULT_PAGE, Constant.DEFAULT_PAGE_SIZE,
				Sort.by("nameTab").ascending());
		AboutResponse abouts = aboutService.getPaggingAbout(Constant.DELETE_FLAG_ACTIVE, paging);
		model.addAttribute("abouts", abouts);

		return "/backend/about/viewAbout";
	}

	@RequestMapping(value = "/about/add", method = RequestMethod.GET)
	public String add(Model model) {
		AboutDto about = new AboutDto();
		model.addAttribute("about", about);

		return "/backend/about/addAbout";

	}

	@RequestMapping(value = "/about/add", method = RequestMethod.POST)
	public String doAdd(AboutDto aboutDto, Model model, RedirectAttributes redirect) {
		aboutService.add(aboutDto);
		redirect.addFlashAttribute("successMessage", Message.ADD_SUCCESS);
		return "redirect:/admin/about/";

	}

	@RequestMapping(value = "/about/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		AboutDto aboutDto = aboutService.getDetail(id, 0);

		model.addAttribute("about", aboutDto);
		return "/backend/about/editAbout";

	}

	@RequestMapping(value = "/about/update", method = RequestMethod.POST)
	public String doUpdate(AboutDto aboutDto, Model model, RedirectAttributes redirect) {

		try {
			aboutService.update(aboutDto);
			redirect.addFlashAttribute("successMessage", Message.UPDATE_SUCCESS);
			return "redirect:/admin/about/";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/about/";
	}

	@RequestMapping(value = "/about/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		try {
			aboutService.delete(id);
			redirect.addFlashAttribute("successMessage", Message.DELETE_SUCCESS);
			return "redirect:/admin/about/";
		} catch (Exception e) {
			return "/backend/about/editAbout";
		}
	}
}
