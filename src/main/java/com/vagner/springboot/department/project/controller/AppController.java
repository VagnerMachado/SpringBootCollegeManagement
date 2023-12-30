package com.vagner.springboot.department.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController 
{
	@GetMapping("/")//or @RequestMapping(value = "/", method = RequestMethod.GET)
	public String greeting()
	{
		return "<!DOCTYPE html>" + 
					"<html>" + 
						"<body style = 'background-color:cornsilk;'>" +  
							"<div style = 'text-align:center; margin-top:20px;'>" +
								"<h1>Spring Boot Test</h1>" + 
								"<p>Welcome</p>\r\n" + 
							"</div>" +
						"</body>" + 
				"</html>";
	}
}
