package com.contribe.arbetsprov;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SynchronizedBookList implements BookList {

	Logger logger = LoggerFactory.getLogger(SynchronizedBookList.class);
	
	@Autowired
	Environment env;
	
	TreeMap<Book, Integer> bookStore = new TreeMap<>();
	
	@Override
	public synchronized Book[] list(String searchString) {
		if(searchString == null)
			searchString = "";
		
		TreeSet<Book> res = new TreeSet<Book>();
		
		for(Book book : bookStore.keySet())
			if(book.matchesIgnoreCase(searchString))
				res.add(book);
		
		return res.toArray(new Book[res.size()]);
	}

	@Override
	public synchronized boolean add(Book book, int quantity) {
		if(book == null || quantity < 0)
			return false;
		
		if(bookStore.containsKey(book)) {
			bookStore.put(book, bookStore.get(book) + quantity);
		} else {
			bookStore.put(book, quantity);
		}
		return true;
	}

	@Override
	public synchronized int[] buy(Book... books) {
		if(books == null)
			return new int[0];
		
		int[] res = new int[books.length];
		
		for(int i = 0; i < books.length; i++) {
			
			if(books[i] == null) {
				res[i] = BookList.DOES_NOT_EXIST;
				continue;
			}
			
			if(this.bookStore.containsKey(books[i])) {
				if(bookStore.get(books[i]) > 0) {
					bookStore.put(books[i], bookStore.get(books[i])-1);
					res[i] = BookList.OK;
				} else {
					res[i] = BookList.NOT_IN_STOCK;
				}
			} else {
				res[i] = BookList.DOES_NOT_EXIST;
			}
		}
		
		return res;
	}
	
	@PostConstruct
	public synchronized void init() {
		try {
			logger.info("populating SynchronizedBookList with data from url: " + env.getProperty("arbetsprov.url"));
			
			URL url = new URL(env.getProperty("arbetsprov.url"));
			
			Scanner in = new Scanner(url.openStream(), "UTF-8");
			
			while(in.hasNextLine()) {
				String line = in.nextLine();
				String[] elems = line.trim().split(";");
				
				//stripping commas from numbers formatted like 100,000,000
				elems[2] = elems[2].replaceAll(",", "");

				if(elems.length == 4) {
					logger.info("adding book: "  + line);
					
					try {
						Book book = new Book(elems[0], elems[1], elems[2]);
						
						this.add(book, Integer.parseInt(elems[3]));
					} catch(NumberFormatException E) {
						logger.error("number formatting error when parsing bookstoredata from url; possible error sources: {" + elems[2] + "," + elems[3] + "}");
					}
				} else {
					logger.warn("source format incompatible at Bookstoredata, book not added: " + line);
				}
				
			}
			logger.info("BookList populated with Bookstoredata from url");
			in.close();
		} catch(IOException E) {
			logger.error("unable to populate BookList with data from url: " + E.toString());
		}
		
	}

}
