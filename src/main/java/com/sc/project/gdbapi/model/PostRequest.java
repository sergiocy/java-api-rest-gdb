package com.sc.project.gdbapi.model;

/**
 * Created by scordoba on 12/25/2017.
 */
import com.squareup.okhttp.*;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by scordoba on 12/25/2017.
 */
public class PostRequest {

    private static final Logger log = LoggerFactory.getLogger(PostRequest.class);
    private String endp = "";
    private String currentQuery = "";
    private String userPass = "";
    private String encoderValue = "";
    private String mediaTypeValue = "";
    private String contentTypeValue = "";
    private String acceptValue = "";

    public PostRequest(String endp, String userPass, String currentQuery, String encoderValue, String mediaTypeValue, String contentTypeValue, String acceptValue){
        this.endp = endp;
        this.currentQuery = currentQuery;
        this.userPass = userPass;
        this.encoderValue = encoderValue;
        this.mediaTypeValue = mediaTypeValue;
        this.contentTypeValue = contentTypeValue;
        this.acceptValue = acceptValue;
    }


    /**
     * java - client for graphDB using POST-request
     */
    public Response generatePostRequest(/*String endp, String currentQuery*/) throws IOException {
        log.info("ATTACHING ENDPOINT: {}", this.endp);
        log.info("SEND SPARQL QUERY: \"{}\"", this.currentQuery);

        String params  = "query="+  URLEncoder.encode(this.currentQuery,this.encoderValue);
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse(this.mediaTypeValue);
        RequestBody body = RequestBody.create(mediaType, params);
        Request request = new Request.Builder().url(endp).post(body)
                .addHeader("Content-Type", this.contentTypeValue)
                //.addHeader("cache-control", "no-cache")
                .addHeader("Accept", this.acceptValue)
                .build();

        long startTime = System.currentTimeMillis();
        Response response = client.newCall(request).execute();
        long endTime = System.currentTimeMillis();

        log.info("Elapsed time : {}", (endTime-startTime));
        log.info(response.body().string());
        //System.out.println(response.body().string());
//   if (response.code() >= 400){
//       throw new QueryExecutionException(response.message());
//   }
        return response;
    }
    
    
    public HttpResponse generatePostRequestV2(){
        log.info("ATTACHING ENDPOINT: {}", this.endp);
        log.info("SEND SPARQL QUERY: \"{}\"", this.currentQuery);
    	
    	HttpClient httpclient = HttpClients.createDefault();
    	HttpPost httppost = new HttpPost(this.endp);

    	// Request parameters and other properties.
    	List<NameValuePair> params = new ArrayList<NameValuePair>(2);
    	params.add(new BasicNameValuePair("query", this.currentQuery));
    	try {
			httppost.setEntity(new UrlEncodedFormEntity(params, this.encoderValue));
			httppost.addHeader("Content-Type", this.contentTypeValue);
			httppost.addHeader("Accept", this.acceptValue);
            httppost.addHeader("authorization", this.userPass);
		} 
    	catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

    	//Execute and get the response.
    	HttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
		} 
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		
		log.info("POST REQUEST: " + response.toString());
		return response;
    }

}
