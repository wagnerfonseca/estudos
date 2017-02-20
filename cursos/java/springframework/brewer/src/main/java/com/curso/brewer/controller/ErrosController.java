package com.curso.brewer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrosController {
	
	@RequestMapping("/404")
	public String paginaNaoEncontrada() {
		return "404";
	}

}
