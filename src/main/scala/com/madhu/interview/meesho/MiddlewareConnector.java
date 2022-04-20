package com.madhu.interview.meesho;

public class MiddlewareConnector {

    private String token; //header
    private String query; // param
    //private String endpointAPI; // GET



    public String retrieveDataLink(String token, String query)  {
        //create requestObject
        //add token to headers
        // add query as request parameters

        //invoke the endpoint using requestObject
        return "";
    }

    public void retrieveAnsStoreData(String token, String query, String filePath)  {
        String fileLink = retrieveDataLink(token, query);
        readFileAndStoreToBlob(fileLink);
    }

    public void readFileAndStoreToBlob(String fileLink) {
        //using blob storage API download
        // upload to downstream blob storage
    }

}
