package org.web.multithreaded.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedCrawler{

	private TreeNode tree = new TreeNode(null);
	TreeNode links = new TreeNode(null);

	public TreeNode crawl(String url) {
		if (tree.data == null) {
			tree.data = url;
		}
		CrawlerThread worker = new CrawlerThread(url, tree);
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		executorService.execute(worker);
		executorService.shutdown();
		return tree;		
		
	}
	
	public static void main(String[] args) {
		MultiThreadedCrawler crawler = new MultiThreadedCrawler();
		crawler.crawl("http://www.redhat.com");
}

}
