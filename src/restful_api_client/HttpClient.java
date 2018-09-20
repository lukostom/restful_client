package restful_api_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Tomasz Lukos
 * class to make HTTP calls and return response back
 */
public class HttpClient {
	
	/**
	 * @param url to the JSON source page
	 * @return plain string response from calling specific URL
	 */
	public String getJsonResponse(String urlLocation){
		StringBuilder tempResult = new StringBuilder();
		try {
			// create URL object with url location provided
			URL url = new URL(urlLocation);
			// create connection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// set headers of the request
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

			// if response code <> 200 then something went wrong
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			// read response into buffer reader
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			// and then into string
			String responseLine;
			while ((responseLine = br.readLine()) != null) {
				tempResult.append(responseLine);
			}
			
			// release conncetion
			conn.disconnect();
		
		// deal with problems
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// finally return result
		return tempResult.toString();
	}
}
