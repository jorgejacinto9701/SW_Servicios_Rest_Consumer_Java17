package com.cibertec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ClienteController {

	
	@RequestMapping("/verCrudCliente")
	public String verBoleta() {
		return "crudCliente";
	}  
	
	
}
