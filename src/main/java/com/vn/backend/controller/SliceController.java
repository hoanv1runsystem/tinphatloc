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

import com.vn.backend.dto.SliceDto;
import com.vn.backend.response.SliceResponse;
import com.vn.backend.service.SliceService;
import com.vn.utils.Constant;

@Controller
@RequestMapping("/admin")
public class SliceController {

	@Autowired
	private SliceService sliceService;

	@RequestMapping("/slice")
	public String view(Model model) {

		Pageable paging = PageRequest.of(0, 20, Sort.by("id").ascending());
		SliceResponse result = sliceService.getPagging(0, paging);

		model.addAttribute("slices", result);
		return "/backend/slice/viewSlice";

	}

	public String detail(Model model) {
		return "/backend/slice/detailSlice";

	}

	@RequestMapping(value = "/slice/add", method = RequestMethod.GET)
	public String add(Model model) {
		SliceDto slice = new SliceDto();
		model.addAttribute("slice", slice);

		return "/backend/slice/addSlice";

	}

	@RequestMapping(value = "/slice/add", method = RequestMethod.POST)
	public String doAdd(SliceDto dto, Model model, RedirectAttributes redirect) throws Exception {

		SliceResponse resutl = sliceService.addValidate(dto);
		if (Constant.STATUS_SUCCSESS == resutl.getStatus()) {
			redirect.addFlashAttribute("successMessage", resutl.getMessage());
			return "redirect:/admin/slice/";
		} else {
			model.addAttribute("message", resutl.getMessage());
			model.addAttribute("slice", dto);
			return "/backend/slice/addSlice";
		}

	}

	@RequestMapping(value = "/slice/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		SliceDto slice = sliceService.getDetail(id, 0);

		model.addAttribute("slice", slice);
		return "/backend/slice/editSlice";

	}

	@RequestMapping(value = "/slice/update", method = RequestMethod.POST)
	public String doUpdate(SliceDto dto, Model model, RedirectAttributes redirect) throws Exception {
		sliceService.update(dto);
		redirect.addFlashAttribute("successMessage", "Cập nhật danh mục thành công!");
		return "redirect:/admin/slice/";

	}

	@RequestMapping(value = "/slice/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		try {
			sliceService.delete(id);
			redirect.addFlashAttribute("successMessage", "Xóa slice thành công!");
			return "redirect:/admin/slice/";
		} catch (Exception e) {
			redirect.addFlashAttribute("successMessage", "Xóa slice thất bại!");
			return "/backend/slice/slice";
		}
	}

}
