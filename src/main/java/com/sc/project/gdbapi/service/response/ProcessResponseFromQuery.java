package com.sc.project.gdbapi.service.response;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;


//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
//import org.json.simple.parser.ParseException;
//import org.springframework.http.HttpEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.google.gson.Gson;

/**
 * Created by scordoba on 12/25/2017.
 *
 * Class for to format JSON response to another JSON as we want
 */

public class ProcessResponseFromQuery {
	
	private static final Logger log = LoggerFactory.getLogger(ProcessResponseFromQuery.class);
	//private Response res = null;
	private HttpResponse res = null;
	private HttpEntity resEntity = null;
	private String resString = "";
	private JSONObject resJson = null;
	private ResponseQuery resProcessed= null;
	
	
	public ProcessResponseFromQuery(/*Response res*/ HttpResponse res){
		log.info("CREATING OBJECT TO PROCESS RESPONSE");
		
		this.res = res;
		this.resEntity = res.getEntity();
		this.resString = this.toGetResponseAsString();
		this.resJson = this.toGetResponseAsJson();
	}

	public HttpEntity getResEntity(){
		return this.resEntity;
	}
	public HttpResponse getRes(){
		return this.res;
	}
	public String getResString(){
		return this.resString;
	}
	public JSONObject getResJson(){
		return this.resJson;
	}
	public ResponseQuery getResProcessed(){
		return this.resProcessed;
	}
	
	
	
	private String toGetResponseAsString() {
		String str = "";
		try {
			str = EntityUtils.toString(this.resEntity);
		} 
		catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	
	private JSONObject toGetResponseAsJson(){
		JSONObject jsonObj = null;
		JSONParser jsonParser = null;
		//Gson g = new Gson();
		
		try {	
			jsonParser = new JSONParser(); 
			jsonObj = (JSONObject) jsonParser.parse(this.resString);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		} 
		catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		} 
		
		return jsonObj;
	}

/*
...functions to get values of keys in response JSON...

	public JSONObject toGetKeyVarsInResponse(JSONObject inJson){
		JSONObject vars = null;
		vars = (JSONObject) inJson.get("head");
		return vars;
	}
	public JSONObject toGetKeyResultsInResponse(JSONObject inJson){
		JSONObject results = null;
		results = (JSONObject) inJson.get("results");
		return results;
	}
*/
}
