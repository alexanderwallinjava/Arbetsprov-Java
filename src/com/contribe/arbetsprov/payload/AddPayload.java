package com.contribe.arbetsprov.payload;

import com.contribe.arbetsprov.Book;

public class AddPayload {
	
	public Book book;
	public int amount;
	
	public AddPayload(Book book, int amount) {
		this.book = book;
		this.amount = amount;
	}
	
	public AddPayload() {}
	
	public String toString() {
		return book.toString() + ": " + amount;
	}

}
