package com.concurrente.trabajopractico.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.concurrente.trabajopractico.service.SequentialSearchService;

@Controller
public class MainController {

    @Autowired
    SequentialSearchService secuencialService;
    
	@RequestMapping("/")
	public String getIndexPage(){
		return "mainPage";
	}

}