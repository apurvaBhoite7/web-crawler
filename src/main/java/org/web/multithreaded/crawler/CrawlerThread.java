package org.web.multithreaded.crawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerThread implements Runnable {

	private Set<String> visited = new ConcurrentSkipListSet<String>();
	private static Set<String> unvisited = new ConcurrentSkipListSet<String>();

	private String url;
	static TreeNode links;
   
	public CrawlerThread(String url, TreeNode links)
	{
		this.url = url;
		this.links = links;
	}

	@Override
	public void run() {
		
		System.out.println(visitPage(url));
		
	}

	private TreeNode visitPage(String url) {
		synchronized (this) {
			visited.add(url);
			unvisited.remove(url);
		}
		if (!url.contains("javascript") && !url.contains("#")) {
			try {
				String rootBaseUri = extractBaseUri(url);
				Connection connection = Jsoup.connect(url);
				Document doc = connection.timeout(0).get();
				Elements linkTags = doc.select("a[href]");
				for (Element e : linkTags) {
					String absoluteLink = e.absUrl("href");
					String linkBaseUri = extractBaseUri(absoluteLink);
					String link = e.attr("abs:href");
					if (linkBaseUri.length() > 0 && rootBaseUri.length() > 0) {
						URL linkUri = toURL(linkBaseUri);
						URL rootUri = toURL(rootBaseUri);
						if (linkUri != null && rootUri != null) {
							String rootUriHost = rootUri.getHost();
							String linkUriHost = linkUri.getHost();
							if (linkUriHost.contains(rootUriHost)) {
								if (!link.contains("javascript") && !link.equals(url)) {
									if (link.startsWith("http") || link.startsWith("www")) {

										String linkAttribute = e.attr("href");
										links.addChild(linkAttribute);
										unvisited.add(link);

									} else if (link.startsWith("/")) {
										String link1 = e.attr("href");
										links.addChild(link1);
										unvisited.add(link);
									}
								}
							}
						}
					}
				}
				return links;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
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

	public static Set<String> getUnvisited() {

		return unvisited;
	}

}
