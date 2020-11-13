package org.web;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Client {

	private static final String REQUEST_URL = "http://localhost:8080/crawl";

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

		Scanner scannerObj = new Scanner(System.in);

		String userUrl = scannerObj.nextLine();
		int userdepth = scannerObj.nextInt();

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(REQUEST_URL + "?url=" + userUrl + "&depth=" + userdepth)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		System.out.println(response.body());

	}

}