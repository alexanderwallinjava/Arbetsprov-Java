package com.contribe.arbetsprov.client;

import org.json.JSONException;

import com.contribe.arbetsprov.supplied.Book;

import ch.qos.logback.classic.Level;

public class TestMain {
	
	public static void main(String[] args) throws JSONException {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.OFF);
		
		RESTClientBookList api = new RESTClientBookList();
		
		System.out.println(api.list("").length);
		for(Book book : api.list(""))
			System.out.println(book);
		
		Book tmp = new Book("title with space", "author", "10");
		System.out.println(api.add(tmp,1));
		
		System.out.println(api.buy(tmp)[0]);
		
	}

}
