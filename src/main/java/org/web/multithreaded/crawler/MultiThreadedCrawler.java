package org.web.multithreaded.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedCrawler {

	private TreeNode tree = new TreeNode(null);
	TreeNode links = new TreeNode(null);

	public TreeNode crawl(String url) throws InterruptedException {
		if (tree.data == null) {
			tree.data = url;
		}
		CrawlerThread worker = new CrawlerThread(url, tree);
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(worker);
		executorService.shutdown();
		return worker.getResult();

	}

	public static void main(String[] args) throws InterruptedException {
		MultiThreadedCrawler crawler = new MultiThreadedCrawler();
		System.out.println(crawler.crawl("http://www.redhat.com"));

	}

}