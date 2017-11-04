package com.contribe.arbetsprov.client;


import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.contribe.arbetsprov.Book;
import com.contribe.arbetsprov.payload.AddPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Level;

public class TestMain {
	
	public static void main(String[] args) throws JSONException {
		
		JSONObject tmp = new JSONObject();
		tmp.put("moo", "moo");
		System.out.println(tmp.toString());
		
		Book tmpBook = new Book("a", "b", "10");
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			tmp.put("test", mapper.writeValueAsString(new AddPayload(tmpBook, 2)));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(tmp.toString());
		
		JSONObject objRec = new JSONObject(tmp.toString());
		System.out.println(objRec.get("test"));
		try {
			System.out.println(mapper.readValue(objRec.getString("test"), AddPayload.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.exit(0);
		
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
