package com.ejemplo.tienda_tecnologica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrincipalController {
	@GetMapping("/")
	public String vista() {
		return "inicio";
	}
		
}