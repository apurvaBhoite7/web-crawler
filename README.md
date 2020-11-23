# web-crawler

**Prerequisites:**
- Java 1.8 
- Maven 

**Server:**
Spring boot application for web crawler

- Running tests
   -  `mvn clean test`
- How to run server
  -  `mvn spring-boot:run`
- To test the application
  -  `curl "http://127.0.0.1:8080/crawl?url=http://www.redhat.com&depth=1"`

 
**Client:**
 - I have implemented client as simple Java program
```
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

```

**Assumption:**
- Depth: Depth in the code indicates how many internal pages can the web crawler crawl. I have considered the maximum depth to be 10 which means the web crawler can crawl upto 10 internal pages.While sending the request to server one can give the depth in range of 0 to 10.

**To do**
- [ ] Implement using multiple threads to reduce the execution time.
