package com.sc.project.gdbapi.controller;


import com.sc.project.gdbapi.model.PostRequest;
import com.sc.project.gdbapi.service.query.SparqlQuery;
import com.sc.project.gdbapi.service.response.ProcessResponseFromQuery;
import com.sc.project.gdbapi.service.response.ResponseQuery;

//import org.json.simple.JSONArray;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.apache.http.HttpEntity;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
//import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

/**
 * Created by scordoba on 12/25/2017.
 */
@RestController
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    String endpRepo = "http://localhost:7200/repositories/scientific-info";

    //@Value("${database.triplets.endpoint}")
    //private String endRepo;


    @RequestMapping(value = "/querying", method = RequestMethod.GET)
    public ResponseQuery launchQuery(@RequestParam(value="intent", defaultValue="resource") String intent,
                                     @RequestParam(value="tags", defaultValue="") String[] tags) {
        log.info("REQUESTING POST");

        
        
        // ...POST WITH "generatePostRequest()" METHOD...
        //Response res = null;s
        //try {
        	// ...we take the response...
            //res = pstReq.generatePostRequest(); 
        	// ...and we manage the query response...
        	//ResponseBodyFromQuery resBody = new ResponseBodyFromQuery(res);
        //} 
        //catch (IOException e) {
        //    e.printStackTrace();
        //}
        // System.out.println(res.toString());
        
        
        
        // ...BUILD QUERY...
        SparqlQuery qryObj = new SparqlQuery(intent, tags);;
        String qry = qryObj.getQry();


        // ...SEND POST REQUEST WITH METHOD "generatePostRequestV2()"...
        PostRequest postReq = new PostRequest(endpRepo, qry, null, null, null, null);
        HttpResponse httpRes = postReq.generatePostRequestV2();
       

        // ...READ AND PROCESS RESPONSE CONTENT WITH OBJECTS "ResponseBodyFromQuery"...
        String content = "";
        ProcessResponseFromQuery httpResBody = null;
		try {
		    // ...we send the response to build a JSON as response...
			httpResBody = new ProcessResponseFromQuery(httpRes);

			content = httpResBody.getResString();
			log.info("CONTENT RECOVERED: {}", content);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}


		//JSONObject vars = httpResBody.toGetKeyVarsInResponse(httpResBody.getResJson());
        JSONObject vars = (JSONObject) httpResBody.getResJson().get("head");
        //JSONObject results = httpResBody.toGetKeyResultsInResponse(httpResBody.getResJson());
        JSONObject results = (JSONObject) httpResBody.getResJson().get("results");
        //JSONArray varsArray = (JSONArray) vars.get("vars");
        //JSONArray resultsArray = (JSONArray) results.get("bindings");


        return new ResponseQuery(vars, results);
    }



}


