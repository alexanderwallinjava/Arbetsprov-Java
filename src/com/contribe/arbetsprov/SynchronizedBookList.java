package com.contribe.arbetsprov;

import java.util.TreeMap;
import java.util.TreeSet;

public class SynchronizedBookList implements BookList {

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

}
