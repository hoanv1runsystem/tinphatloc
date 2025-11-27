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

import com.vn.backend.dto.UserDto;
import com.vn.backend.response.UserResponse;
import com.vn.backend.service.UserService;
import com.vn.utils.Constant;
import com.vn.utils.Message;

@Controller
@RequestMapping("/admin")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/user")
	public String view(Model model) {
		Pageable paging = PageRequest.of(Constant.DEFAULT_PAGE, Constant.DEFAULT_PAGE_SIZE,
				Sort.by("username").ascending());
		UserResponse users = userService.getPaggingUser(Constant.DELETE_FLAG_ACTIVE, paging);
		model.addAttribute("users", users);
		return "/backend/user/viewUser";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String add(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
		return "/backend/user/addUser";

	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String doAdd(UserDto userDto, Model model, RedirectAttributes redirect) throws Exception {

		UserResponse resutl = userService.addVaildate(userDto);
		if (Constant.STATUS_SUCCSESS != resutl.getStatus()) {
			model.addAttribute("message", resutl.getMessage());
			model.addAttribute("user", resutl.getUser());
			return "/backend/user/addUser";
		} else {
			redirect.addFlashAttribute("successMessage", resutl.getMessage());
			return "redirect:/admin/user/";
		}
	}

	@RequestMapping(value = "/user/update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
		UserDto userDto = userService.getDetail(id, 0);

		model.addAttribute("user", userDto);
		return "/backend/user/updateUser";

	}

	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public String doUpdate(UserDto userDto, Model model, RedirectAttributes redirect) {

		try {
			userService.update(userDto);
			redirect.addFlashAttribute("successMessage", Message.UPDATE_SUCCESS);
			return "redirect:/admin/user/";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/user/";
	}

	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, Model model, RedirectAttributes redirect) {
		try {
			userService.delete(id);
			redirect.addFlashAttribute("successMessage", Message.DELETE_SUCCESS);
			return "redirect:/admin/user/";
		} catch (Exception e) {
			return "/backend/user/editUser";
		}
	}

	@RequestMapping(value = "/user/change-password", method = RequestMethod.GET)
	public String changePassword(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
		return "/backend/user/changePassword";

	}

	@RequestMapping(value = "/user/change-password", method = RequestMethod.POST)
	public String doChangePassword(UserDto userDto, Model model, RedirectAttributes redirect) {

		UserResponse resutl = userService.changePassword(userDto);
		if (Constant.STATUS_SUCCSESS != resutl.getStatus()) {
			model.addAttribute("message", resutl.getMessage());
			model.addAttribute("user", resutl.getUser());
			return "/backend/user/changePassword";
		} else {
			redirect.addFlashAttribute("successMessage", resutl.getMessage());
			return "redirect:/admin/user/";
		}
	}

}
