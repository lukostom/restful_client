package restful_api_client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* BELOW IS THE JSON RESPONSE FOR PAGE 1, URL: https://jsonmock.hackerrank.com/api/movies/search/?Title=spiderman&page=1
 * {	
 * 		"page":"1",
 * 		"per_page":10,
 * 		"total":13,	
 * 		"total_pages":2,
 * 		"data":
 * 		[
 * 		{	
 * 			"Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BYjFhN2RjZTctMzA2Ni00NzE2LWJmYjMtNDAyYTllOTkyMmY3XkEyXkFqcGdeQXVyNTA0OTU0OTQ@._V1_SX300.jpg",
 * 			"Title":"Italian Spiderman",
 * 			"Type":"movie",
 * 			"Year":2007,
 * 			"imdbID":"tt2705436"
 * 		},
 * 		{
 * 			"Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BMjQ4MzcxNDU3N15BMl5BanBnXkFtZTgwOTE1MzMxNzE@._V1_SX300.jpg",
 * 			"Title":"Superman, Spiderman or Batman",
 * 			"Type":"movie",
 * 			"Year":2011,
 * 			"imdbID":"tt2084949"
 * 		},
 * 		... more movies
 * 		
 * 		]
 * 	}
 */



/**
 * @author Tomasz
 * this is a controlling class for restful client
 * It will read JSON response (the same type as above) and
 * print list of sorted titles 
 */
public class MainApplication {
	public static void main(String[] args){
		// create list that will store all tiles
		List <String> allTitles = new ArrayList<>();
		
		//https://jsonmock.hackerrank.com/api/movies/search/?Title=spiderman&page=1
		String urlLocation = "https://jsonmock.hackerrank.com/api/movies/search/?Title=spiderman";
		
		// get string response for given url
		String response = new HttpClient().getJsonResponse(urlLocation);
		
		//create converter class and use it to convert string to json and json to json array
		JsonConversion jsonConverter = new JsonConversion();
		
		// get initial response 
		JSONObject initialJson = jsonConverter.convertHttpReponseToJson(response);

		// see how many pages are returned
		int totalPages = 0;
		if (initialJson != null) {
			try {
				totalPages = initialJson.getInt("total_pages");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		// get response (plain and JSON) for each page
		for (int i = 1; i <= totalPages; i++){
			response = new HttpClient().getJsonResponse(urlLocation + "&page=" + i);
			JSONObject jsonResponse = jsonConverter.convertHttpReponseToJson(response);
			
			// get array containing details of each movie
			JSONArray jsonArray = null;
			try {
				 jsonArray = jsonResponse.getJSONArray("data");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			// go through JSON objects in JSON array and get wanted details e.g. title
			if (jsonArray != null){
				for (int j = 0; j < jsonArray.length(); j++){
					try {
						JSONObject js = jsonArray.getJSONObject(j);
						String singleTitle = (String)js.get("Title");
						if (!allTitles.contains(singleTitle)){
							allTitles.add(singleTitle);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		
		// sort titles 
		Collections.sort(allTitles);
		
		// and display them on the screen
		for (String title : allTitles){
			System.out.println(title);
		}
	}
}
