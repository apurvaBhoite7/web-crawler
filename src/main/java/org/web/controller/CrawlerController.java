package org.web.controller;

import java.net.MalformedURLException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.web.crawler.implement.Crawler;
import org.web.crawler.implement.SimpleCrawler;

@RestController
public class CrawlerController {

	@RequestMapping(value = "/crawl", method = RequestMethod.GET, produces="text/plain")
	public String crawl() throws MalformedURLException
	{
	
		Crawler crawler = new SimpleCrawler("https://redhat.com", 2);
		crawler.crawl("https://redhat.com");
		
		//return crawler.getTree().toOutput(0);
		return crawler.getTree().toOutput(0);
	}
}
