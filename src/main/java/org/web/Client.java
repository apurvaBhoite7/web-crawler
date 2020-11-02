package org.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {

	private static final String REQUEST_URL="http://localhost:8080/crawl";
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		 HttpClient client = HttpClient.newHttpClient();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(REQUEST_URL))
	                .build();

	        HttpResponse<String> response = client.send(request,
	                HttpResponse.BodyHandlers.ofString());

	        System.out.println(response.body());

	}

}
