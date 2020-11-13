package org.web.controller;

import java.net.MalformedURLException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.web.crawler.implement.Crawler;
import org.web.crawler.implement.SimpleCrawler;

@RestController
public class CrawlerController {

	@RequestMapping(value = "/crawl", method = RequestMethod.GET, produces = "text/plain")
	@ResponseBody
	public String crawl(@RequestParam(name = "url") String url, @RequestParam(name = "depth") int depth)
			throws MalformedURLException {

		if (depth >= 0 && depth <= 10) {

			Crawler crawler = new SimpleCrawler(url, depth);
			crawler.crawl(url);

			return crawler.getTree().toOutput(0);

		} else {
			return "Enter the depth in range of 0 to 10";
		}
	}

}
