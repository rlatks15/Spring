package com.naver.myhome4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		return "redirect:/member/login";
	}

	@GetMapping("/error/403")
	public String error_403() {
		return "error/403";
	}
	
	@GetMapping("/error/404")
	public String error_404() {
		return "error/404";
	}
	
	@GetMapping("/error/error")
	public String error_error() {
		return "error/error";
	}
}
