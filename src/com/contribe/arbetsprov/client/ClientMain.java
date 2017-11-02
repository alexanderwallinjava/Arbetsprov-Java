package com.contribe.arbetsprov.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.contribe.arbetsprov.Book;
import com.contribe.arbetsprov.BookList;

public class ClientMain {
	
	public static void main(String[] args) throws RestClientException, UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
		Book[] books = restTemplate.getForObject("http://localhost:8080/search", Book[].class);
		
		System.out.println(books.length);
		
		for(Book book : books)
			System.out.println(book);
		
		System.out.println("\n\n");
		
		Book newBook = new Book("title", "author", "1.5");
		int amount = 3;
		System.out.println(newBook);
		
		boolean success = restTemplate.getForObject("http://localhost:8080/add?title=" + URLEncoder.encode(newBook.getTitle(), "UTF-8") + 
				"&author=" + URLEncoder.encode(newBook.getAuthor(), "UTF-8") + 
				"&price=" + URLEncoder.encode(newBook.getPrice().toString(), "UTF-8") + 
				"&amount=" + URLEncoder.encode(amount+"", "UTF-8"), Boolean.class);
		
		System.out.println(success);
		
		books = restTemplate.getForObject("http://localhost:8080/search", Book[].class);
		
		System.out.println(books.length);
		
		for(Book book : books)
			System.out.println(book);
		
		
		StringBuilder buyText = new StringBuilder();
		
		ArrayList<Book> booksToBuy = new ArrayList<>();
		booksToBuy.add(newBook);
		
		Book unknownBook = new Book("unknown", "unknown", "0");
		booksToBuy.add(unknownBook);
		
		for(Book book : booksToBuy) {
			if(buyText.length() > 0)
				buyText.append("&");
			buyText.append("title=" + URLEncoder.encode(book.getTitle(), "UTF-8") + "&");
			buyText.append("author=" + URLEncoder.encode(book.getAuthor(), "UTF-8") + "&");
			buyText.append("price=" + URLEncoder.encode(book.getPrice().toString(), "UTF-8"));
		}
		
		Integer[] res = restTemplate.getForObject("http://localhost:8080/buy?" + buyText.toString(), Integer[].class);
		
		for(int i = 0; i < res.length; i++) {
			String successString = res[i] == BookList.OK ? "OK" : ( res[i] == BookList.NOT_IN_STOCK ? "NOT IN STOCK" : "DOES NOT EXIST");
			
			System.out.println(res[i] +" (" + successString + ")\t");
		}
			
	}

}
