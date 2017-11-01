package com.contribe.arbetsprov;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SynchronizedBookList implements BookList {

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
	public void init() throws IOException {
		System.out.println("initializing SynchronizedBookList");
		
		URL url = new URL(env.getProperty("arbetsprov.url"));
		
		Scanner in = new Scanner(url.openStream(), "UTF-8");
		
		while(in.hasNextLine()) {
			String line = in.nextLine();
			String[] elems = line.trim().split(";");
			System.out.println(line);
			if(elems.length == 4) {
				Book book = new Book(elems[0], elems[1], elems[2].replaceAll(",", ""));
				this.add(book, Integer.parseInt(elems[3]));
				
				System.out.println("added book");
			} else {
				//logga
			}
		}
		in.close();
		
		System.out.println(env.getProperty("arbetsprov.url"));
	}

}
