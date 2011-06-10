/**
 * QuakeFelt: A humanitarian project for Random hacks of Kindness (Melbourne)
 * API.java
 *
 * @author James Giang
 *
 * Copyright 2011 James Giang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
 
package com.jamesgiang.quakefelt;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

public class API {
	private static InputStream iostream = null;
	private static HttpClient httpclient = new DefaultHttpClient();
	private static String content = null;
	
	public static JSONArray getTenQuakes() throws ClientProtocolException, IOException, JSONException {
       	HttpResponse response = httpclient.execute(new HttpGet("http://dev.jamesgiang.com/quakefelt/quakes.php")); 
        iostream = response.getEntity().getContent();
        content = Utils.ReadInputStream(iostream);
    	JSONArray json = new JSONArray(content);
		return json;
	}
	
	public static JSONArray getResponses(int id) throws ClientProtocolException, IOException, JSONException {
		if(id == 3052276) {
			HttpResponse response = httpclient.execute(new HttpGet("http://dev.jamesgiang.com/quakefelt/3052276/responses.php")); 
	       	//HttpResponse response = httpclient.execute(new HttpGet("http://quakefelt.xzyfer.com/frontend_dev.php/quake/" + id + "/reports.json")); 
	        iostream = response.getEntity().getContent();
	        content = Utils.ReadInputStream(iostream);
	    	JSONArray json = new JSONArray(content);
			return json;
			
		} else {
			JSONArray json = new JSONArray("[]");
			return json;
		}
	}
}