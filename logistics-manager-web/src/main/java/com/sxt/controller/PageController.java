package com.sxt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	@RequestMapping("/")
	public String showMain(){
		return "main";
	}
	@RequestMapping("/{path}")
	public String show(@PathVariable String path){
		return path;
	}
	
	/*@RequestMapping("/index")
	public String showIndex(){
		return "index";
	}
	
	@RequestMapping("/top")
	public String showTop(){
		return "top";
	}
	
	@RequestMapping("/left")
	public String showLeft(){
		return "left";
	}*/
}