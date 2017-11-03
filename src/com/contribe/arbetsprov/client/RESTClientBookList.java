package com.contribe.arbetsprov.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.contribe.arbetsprov.Book;
import com.contribe.arbetsprov.BookList;

public class RESTClientBookList implements BookList {

	RestTemplate restTemplate = new RestTemplate();
	
	@Override
	public Book[] list(String searchString) {
		if(searchString == null)
			searchString = "";
		
		try {
			if(searchString.length() == 0)
				return restTemplate.getForObject("http://localhost:8080/search", Book[].class);
			
			return restTemplate.getForObject("http://localhost:8080/search?search=" + URLEncoder.encode(searchString, "UTF-8"), Book[].class);
		} catch (UnsupportedEncodingException | RestClientException e) {
			return new Book[0];
		}
	}

	@Override
	public boolean add(Book book, int quantity) {
		try {
			return restTemplate.getForObject("http://localhost:8080/add?title=" + 
					URLEncoder.encode(book.getTitle(), "UTF-8") + 
					"&author=" + URLEncoder.encode(book.getAuthor(), "UTF-8") + 
					"&price=" + URLEncoder.encode(book.getPrice().toString(), "UTF-8") + 
					"&amount=" + quantity, Boolean.class);
		} catch (UnsupportedEncodingException | RestClientException e) {
			return false;
		}
	}

	@Override
	public int[] buy(Book... books) {
		
		StringBuilder buyText = new StringBuilder();
		try {
			for(Book book : books) {
				if(buyText.length() > 0)
					buyText.append("&");
				buyText.append("title=" + URLEncoder.encode(book.getTitle(), "UTF-8") + "&");
				buyText.append("author=" + URLEncoder.encode(book.getAuthor(), "UTF-8") + "&");
				buyText.append("price=" + book.getPrice().toString());
			}
		} catch (UnsupportedEncodingException | RestClientException e) {
			int[] res = new int[books.length];
			
			for(int i = 0; i < res.length; i++)
				res[i] = BookList.DOES_NOT_EXIST;
			return res;
		}
		
		Integer[] tmp = restTemplate.getForObject("http://localhost:8080/buy?" + buyText.toString(), Integer[].class);
		
		int[] res = new int[tmp.length];
		
		for(int i = 0; i < res.length; i++)
			res[i] = tmp[i];
		
		return res;
	}
}
