package com.ssafy.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.demo.service.PostService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/test")
public class MainController {
	
	@Autowired
	private PostService service;
	
	@GetMapping("test")
	public String test() {
		return "test";
	}
	@GetMapping("/")
	public String main() {
		return "main";
	}
	
}
