package restful_api_client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* BELOW IS THE JSON RESPONSE FOR PAGE 1, URL: https://jsonmock.hackerrank.com/api/movies/search/?Title=spiderman&page=1
{
    "count": 87, 
    "next": "https://swapi.co/api/people/?page=2", 
    "previous": null, 
    "results": [
        {
            "name": "Luke Skywalker", 
            "height": "172", 
            "mass": "77", 
            "hair_color": "blond", 
            "skin_color": "fair", 
            "eye_color": "blue", 
            "birth_year": "19BBY", 
            "gender": "male", 
            "homeworld": "https://swapi.co/api/planets/1/", 
            "films": [
                "https://swapi.co/api/films/2/", 
                "https://swapi.co/api/films/6/", 
                "https://swapi.co/api/films/3/", 
                "https://swapi.co/api/films/1/", 
                "https://swapi.co/api/films/7/"
            ], 
            "species": [
                "https://swapi.co/api/species/1/"
            ], 
            "vehicles": [
                "https://swapi.co/api/vehicles/14/", 
                "https://swapi.co/api/vehicles/30/"
            ], 
            "starships": [
                "https://swapi.co/api/starships/12/", 
                "https://swapi.co/api/starships/22/"
            ], 
            "created": "2014-12-09T13:50:51.644000Z", 
            "edited": "2014-12-20T21:17:56.891000Z", 
            "url": "https://swapi.co/api/people/1/"
        }, 
  		... more people
  		
  		]
  	}
 */



/**
 * @author Tomasz Lukos
 * this is a controlling class for restful client
 * It will read JSON response (the same type as above) and
 * print list of sorted names
 */
public class MainApplication {
	public static void main(String[] args) throws JSONException{
		// create list that will store all people 
		List <String> allPeople = new ArrayList<>();
		
		// main url
		String urlLocation = "https://swapi.co/api/people/";
		
		// next page, starting with initial url
		String nextPage = urlLocation;
		
		// response string
		String response;
		
		// Json converter to convert string response into JSON
		JsonConversion jsonConverter = new JsonConversion();
		
		// get response (plain and JSON) for each page
		while (!nextPage.equals("null")) {
			response = new HttpClient().getJsonResponse(nextPage);
			JSONObject jsonResponse = jsonConverter.convertHttpReponseToJson(response);
			
			// get array containing details of each movie
			JSONArray jsonArray = null;
			try {
				 jsonArray = jsonResponse.getJSONArray("results");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			// go through JSON objects in JSON array and get wanted details e.g. title
			if (jsonArray != null){
				for (int j = 0; j < jsonArray.length(); j++){
					try {
						JSONObject js = jsonArray.getJSONObject(j);
						String singleName = (String)js.get("name");
						if (!allPeople.contains(singleName)){
							allPeople.add(singleName);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			
			// get next page
			nextPage = jsonResponse.getString("next");
		}
		
		// sort people
		Collections.sort(allPeople);
		
		int counter = 1;
		// and display them on the screen
		for (String name : allPeople){
			System.out.printf("%5d%50s\n",counter, name);
			counter++;
		}
	}
}
