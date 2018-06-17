package restful_api_client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Tomasz
 * this class will work with JSON object e.g. convert string to JSON
 */
public class JsonConversion { 
	
	/**
	 * @param response plain string response
	 * @return JSONObject obtained from String response, null if conversion failed
	 */
	public JSONObject convertHttpReponseToJson(String response){
		JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	

	/**
	 * @param JSONObject which is the parent object which contain 
	 * JSON array as one of its values identified by whichKey
	 * @param whichKey string value representing key with JSON array as its value
	 * @return SON array which is simply array containing JSON objects
	 */
	public JSONArray getJsonArrayFromJsonObject(JSONObject json, String whichKey){
		JSONArray jsonArray = null;
		if (json != null){
			try {		
				jsonArray = json.getJSONArray("data");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jsonArray;
	}
}
