package com.contribe.arbetsprov.client;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.contribe.arbetsprov.payload.AddPayload;
import com.contribe.arbetsprov.supplied.Book;
import com.contribe.arbetsprov.supplied.BookList;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A class that mirrors a BookList implementation reached using a REST-service
 */
public class RESTClientBookList implements BookList {

	RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public Book[] list(String searchString) {
		
		if(searchString == null)
			searchString = "";
		
		JSONObject request = new JSONObject();
		try {
			request.put("search", searchString);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
			
			Book[] data = restTemplate.postForObject("http://localhost:8080/search", entity, Book[].class);
			
			return data;
		} catch (JSONException e) {
			return new Book[0];
		}
		
		
	}

	@Override
	public boolean add(Book book, int quantity) {
		if(book == null)
			return false;
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(mapper.writeValueAsString(new AddPayload(book, quantity)), headers);
			
			return restTemplate.postForObject("http://localhost:8080/add", entity, Boolean.class);
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public int[] buy(Book... books) {
		
		if(books == null)
			return new int[0];
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(mapper.writeValueAsString(books), headers);
			
			Integer[] tmp = restTemplate.postForObject("http://localhost:8080/buy", entity, Integer[].class);
			
			int[] res = new int[tmp.length];
			
			for(int i = 0; i < res.length; i++)
				res[i] = tmp[i];
			
			return res;
		} catch (IOException e) {
			int[] res = new int[books.length];
			for(int i = 0; i < res.length; i++)
				res[i] = BookList.DOES_NOT_EXIST;
			
			return res;
		}
	}
}
