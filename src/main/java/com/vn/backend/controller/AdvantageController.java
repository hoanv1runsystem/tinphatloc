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

import com.vn.backend.dto.AdvantageDto;
import com.vn.backend.response.AdvantageResponse;
import com.vn.backend.service.AdvantageService;
import com.vn.utils.Constant;
import com.vn.utils.Message;

@Controller
@RequestMapping("/admin")
public class AdvantageController {

	@Autowired
	private AdvantageService advantageService;

	@RequestMapping("/advantage")
	public String view(Model model) {

		Pageable paging = PageRequest.of(0, 20, Sort.by("name").ascending());
		AdvantageResponse result = advantageService.getPagging(0, paging);

		model.addAttribute("advantages", result);
		return "/backend/advantage/viewAdvantage";

	}

	public String detail(Model model) {
		return "/backend/advantage/detailAdvantage";

	}

	@RequestMapping(value = "/advantage/add", method = RequestMethod.GET)
	public String add(Model model) {
		AdvantageDto advantage = new AdvantageDto();
		model.addAttribute("advantage", advantage);

		return "/backend/advantage/addAdvantage";

	}

	@RequestMapping(value = "/advantage/add", method = RequestMethod.POST)
	public String doAdd(AdvantageDto dto, Model model, RedirectAttributes redirect) throws Exception {

		AdvantageResponse resutl = advantageService.addValidate(dto);
		if (Constant.STATUS_SUCCSESS == resutl.getStatus()) {
			redirect.addFlashAttribute("successMessage", resutl.getMessage());
			return "redirect:/admin/advantage/";
		} else {
			model.addAttribute("message", resutl.getMessage());
			model.addAttribute("advantage", dto);
			return "/backend/advantage/addAdvantage";
		}

	}

	@RequestMapping(value = "/advantage/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		AdvantageDto slice = advantageService.getDetail(id, 0);

		model.addAttribute("advantage", slice);
		return "/backend/advantage/editAdvantage";

	}

	@RequestMapping(value = "/advantage/update", method = RequestMethod.POST)
	public String doUpdate(AdvantageDto dto, Model model, RedirectAttributes redirect) throws Exception {
		advantageService.update(dto);
		redirect.addFlashAttribute("successMessage", Message.UPDATE_SUCCESS);
		return "redirect:/admin/advantage/";

	}

	@RequestMapping(value = "/advantage/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		try {
			advantageService.delete(id);
			redirect.addFlashAttribute("successMessage", Message.DELETE_SUCCESS);
			return "redirect:/admin/advantage/";
		} catch (Exception e) {
			redirect.addFlashAttribute("successMessage", Message.DELETE_FAILURE);
			return "/backend/slice/advantage";
		}
	}

}
