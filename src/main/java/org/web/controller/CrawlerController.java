package org.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlerController {

	@RequestMapping("/crawl") 
	public String crawl()
	{
		return "Hello crawler";
	}
}
