package com.sc.project.gdbapi.service.query;

import java.io.UnsupportedEncodingException;

/**
 * Created by scordoba on 12/25/2017.
 */
public class SparqlQuery {

    String qry = "";

    public SparqlQuery(String intent, String[] tags){
        this.qry = this.generateGeneralSelect(buildIntent(intent), tags);
    }

    public String getQry(){
        return this.qry;
    }



    public String cleanString(String in){
        in = in.toString().
                toLowerCase().
                replaceAll(" ", "_").
                replaceAll("\\.", "-").
                replaceAll("á", "a").
                replaceAll("é", "e").
                replaceAll("í", "i").
                replaceAll("ó", "o").
                replaceAll("ú", "u").
                trim();

        return in;
    }


    public String buildIntent(String intent){

        intent = cleanString(intent);

        if(intent.equals("resource")){
            intent = "resource";
        }
        else if(intent.equals("author")){
            intent = "person";
        }
        else if(intent.equals("organization")){
            intent = "organization";
        }
        else{
            intent = "resource";
        }

        return intent;
    }

    public String generateGeneralSelect(String in, String[] ta){
        StringBuilder q = new StringBuilder();
        q.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ");
        q.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> ");
        q.append("PREFIX owl: <http://www.w3.org/2002/07/owl#> ");
        q.append("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> ");

        q.append("PREFIX topic: <http://semantic-search.com/topic/> ");
        q.append("PREFIX tag: <http://semantic-search.com/tag/> ");
        q.append("PREFIX relation: <http://semantic-search.com/relation/> ");
        q.append("prefix org: <http://semantic-search.com/organization/> ");
        q.append("prefix person: <http://semantic-search.com/person/> ");
        q.append("prefix resource: <http://semantic-search.com/resource/> ");
        q.append("prefix attr: <http://semantic-search.com/attribute/> ");
        q.append("PREFIX : <http://semantic-search.com/> ");


        q.append("SELECT ?individual ?name ?type ?related_first ?name_first ");
        q.append("WHERE { ");

        q.append("BIND(:").append(in).append(" as ?type) . ");
        q.append("?individual rdf:type ?type . ");

        for(String t : ta){
            //q.append("?s relation:hasTag/rdfs:label ?o . ");
            // ...here we filter by tags...
            t = cleanString(t);
            q.append(" ?individual relation:hasTag/rdfs:label \"").append(t).append("\"^^rdfs:Literal . ");
        }

        q.append(" optional{\n" +
                "\t\t?individual attr:name ?name .\n" +
                "\t}\n" +
                "\n" +
                "\toptional{\n" +
                "\t\t?individual relation:relatedTo ?related_first .\n" +
                "\t\t?related_first attr:name ?name_first .\n" +
                "\t}\n" );
                //"\toptional{\n" +
                //"\t\t?related_first relation:relatedTo ?related_second .\n" +
                //"\t\t?related_second attr:name ?name_second .\n" +
                //"\t}
                //");

        q.append(" } ");

        return q.toString();
    }


}
