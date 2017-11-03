package com.contribe.arbetsprov.client;

import java.io.UnsupportedEncodingException;

import org.springframework.web.client.RestClientException;

import com.contribe.arbetsprov.Book;
import com.contribe.arbetsprov.BookList;

import ch.qos.logback.classic.Level;

public class ClientMain {
	
	public static void main(String[] args) throws RestClientException, UnsupportedEncodingException {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.OFF);
		
		BookList api = new RESTClientBookList();
		
		System.out.println(api.list("").length);
		
		Book book = new Book("Min lilla bok", "Alexander Wallin", "10.0");
		
		System.out.println(api.add(book, 2));
		
		System.out.println(api.list("").length);
		
		int[] res = api.buy(book);
		
		System.out.println(res.length);
		
		for(int i = 0; i < res.length; i++)
			System.out.print(res[i] + "\t");
		System.out.println();
	}

}
