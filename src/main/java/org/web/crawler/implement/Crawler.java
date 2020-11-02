package org.web.crawler.implement;

public interface Crawler {

	TreeRepresentation crawl(String url);

	TreeRepresentation getTree();
}
