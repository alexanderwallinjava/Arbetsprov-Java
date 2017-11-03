package com.contribe.arbetsprov.client;


import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.contribe.arbetsprov.Book;

import ch.qos.logback.classic.Level;

public class TestMain {
	
	public static void main(String[] args) throws JSONException {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.OFF);
		
		RestTemplate restTemplate = new RestTemplate();
		
		JSONObject request = new JSONObject();
		request.put("search", "a");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
		
		Book[] data = restTemplate.postForObject("http://localhost:8080/search", entity, Book[].class);
		
		
		System.out.println(data.length);
		
	}

}
