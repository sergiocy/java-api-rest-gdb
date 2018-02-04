package com.sc.project.gdbapi.service.response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by scordoba on 12/25/2017.
 *
 */
public class ResponseQuery {

    private static final Logger log = LoggerFactory.getLogger(ResponseQuery.class);
    private final JSONArray vars;
    private final JSONArray results;


    public ResponseQuery(JSONObject varsJson, JSONObject resultsJson) {
        log.info("FORMATTING RESPONSE TO SHOW");

        this.vars = ((JSONArray) varsJson.get("vars"));
        this.results = (JSONArray) resultsJson.get("bindings");
    }

    public JSONArray getVars() {
        return this.vars;
    }
    public JSONArray getResults() {
        return this.results;
    }
}
