package com.example.user.jsetestapp;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JSONParser {

    // declare variable
    static InputStream inputStream = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    /**
     * Function to get json from url by making HTTP POST or GET method
     * @param url    - url to get the JSON string from
     * @param method - type of http request
     * @param params - list of key values to pass along to the http request
     * @return JSONObject
     */
    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {
        // Make HTTP request
        try {
            // check if request method is POST
            if (method.equals("POST")) {
                // create new DefaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();

                // create new HttpPost
                HttpPost httpPost = new HttpPost(url);
                // add encoded params as entity to httpPost
                httpPost.setEntity(new UrlEncodedFormEntity(params));

                // create response; execute httpPost and assign response to httpResponse
                HttpResponse httpResponse = httpClient.execute(httpPost);
                // get entity from httpResponse
                HttpEntity httpEntity = httpResponse.getEntity();
                // get content from httpEntity and assign its value to inputStream
                inputStream = httpEntity.getContent();

            }
            // if request method is GET
            else if (method.equals("GET")) {
                // create new DefaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();

                // convert param to String and encode
                String paramString = URLEncodedUtils.format(params, "utf-8");
                // prepare url by appending '?' and paramString
                url += "?" + paramString;
                // create new HttpGet with url
                HttpGet httpGet = new HttpGet(url);

                // create response; execute httpGet and assign response to httpGet
                HttpResponse httpResponse = httpClient.execute(httpGet);
                // get entity from httpResponse
                HttpEntity httpEntity = httpResponse.getEntity();
                // get content from httpEntity and assign its value to inputStream
                inputStream = httpEntity.getContent();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // convert result to one String
        try {
            // create new BufferedReader from a new InputStreamReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, "iso-8859-1"), 8);
            // create new StringBuilder
            StringBuilder sb = new StringBuilder();
            // initialize holder for line
            String line;

            // while there are lines
            while ((line = reader.readLine()) != null) {
                // append the 'line' and go to the next line
                sb.append(line).append("\n");
            }

            // close inputStream
            inputStream.close();
            // assign value of string builder content to json
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            // create JSONObject from json string
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

    }
}