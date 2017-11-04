package com.contribe.arbetsprov.payload;

import com.contribe.arbetsprov.supplied.Book;

/**
 * A Jackson-compatible class for sending and receiving "add" through the REST protocol
 */
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
