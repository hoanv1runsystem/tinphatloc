package com.vn.backend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

	@RequestMapping("/admin")
	public String admin() {
		return "backend/admin";
	}

	@RequestMapping(value = "/admin/remove/session", method = RequestMethod.GET)
	public String invalidate(HttpServletRequest request, Model model) {
		 request.getSession().invalidate();
		 return "redirect:/backend/admin"; 
	}
}
