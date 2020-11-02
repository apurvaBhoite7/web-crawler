package org.web.crawler.implement;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SimpleCrawler implements Crawler {

	private static final String SRC = "src";
	private static final String FRAME = "frame";
	private static final String HREF = "href";
	private static final String A_HREF = "a[href]";
	private static final int MAXIMUM_DEPTH = 10;

	private Set<String> visitedLinks;
	private Set<String> links = new HashSet<>();
	private TreeRepresentation tree;
	private String baseUri;
	private Map<String, String> cookies;

	private int depthLimit;

	public SimpleCrawler(String url, int depthLimit) {
		this.baseUri = extractBaseUri(url);
		this.depthLimit = depthLimit;
		this.tree = new TreeRepresentation(url);
		this.visitedLinks = new HashSet<>();

	}

	@Override
	public TreeRepresentation crawl(String url) {
		return this.crawl(url, 0, new HashSet<>(), this.tree);
	}

	private TreeRepresentation crawl(String url, int presentDepth, Set<String> visited, TreeRepresentation t) {
		System.out.println("crawling went to depth: " + presentDepth + " [" + url + "]");
		this.visitedLinks.addAll(visited);
		if ((!visitedLinks.contains(url) || !visitedLinks.contains(appendSlashToUrl(url)))
				&& presentDepth < this.depthLimit && presentDepth < MAXIMUM_DEPTH) {
			try {
				visitedLinks.add(url);
				Connection connection = Jsoup.connect(url);
				if (this.cookies != null) {
					connection.cookies(cookies);
				} else {
					this.cookies = connection.response().cookies();
				}
				Document document = connection.get();
				crawling(document, A_HREF, HREF, this.visitedLinks, t, presentDepth);
				crawling(document, FRAME, SRC, this.visitedLinks, t, presentDepth);
				presentDepth++;
			} catch (IOException e) {
			}
		} else {
			System.out.println("url [" + url + "] already visited");
		}
		return tree;
	}

	private void crawling(Document document, String query, String attributeKey, Set<String> visited, TreeRepresentation t, int presentDepth) {
	    this.visitedLinks.addAll(visited);
	    Elements linksOnPage = document.select(query);
	    for (Element page : linksOnPage) {
	        String absoluteLink = page.absUrl(attributeKey);
	        String rootBaseUri = extractBaseUri(this.baseUri);
	        String linkBaseUri = extractBaseUri(absoluteLink);

	        if (linkBaseUri.length() > 0 && rootBaseUri.length() > 0) {
	            URL linkUri = toURL(linkBaseUri);
	            URL rootUri = toURL(rootBaseUri);
	            if (linkUri != null && rootUri != null) {
	                String rootUriHost = rootUri.getHost();
	                String linkUriHost = linkUri.getHost();

	                if (linkUriHost.contains(rootUriHost)) {
	                    String link = page.attr(attributeKey);
	                    if (!visitedLinks.contains(link) || !visitedLinks.contains(appendSlashToUrl(link))) {
	                        links.add(link);
	                        TreeRepresentation childAsParent = t.addChild(link);
	                        crawl(absoluteLink, presentDepth + 1, visited, childAsParent);
	                    }
	                }
	            }
	        }
	    }
	}

	private String appendSlashToUrl(String url) {
		if (url.charAt(url.length() - 1) != '/') {
			return url + "/";
		}
		return url;
	}

	@Override
	public TreeRepresentation getTree() {
		return tree;
	}

	private URL toURL(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			System.out.println("something is wrong with url: %s" + url);
		}
		return null;
	}

	private String extractBaseUri(String url) {

		URL u = toURL(url);
		if (u == null) {
			return "";
		}
		return u.getProtocol() + "://" + u.getHost() + ":" + u.getPort();
	}

}
