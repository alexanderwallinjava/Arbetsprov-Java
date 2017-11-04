package com.contribe.arbetsprov.supplied;

public interface BookList {

	public static final int OK = 0;
	public static final int NOT_IN_STOCK = 1;
	public static final int DOES_NOT_EXIST = 2;
	
	public Book[] list(String searchString);

	public boolean add(Book book, int quantity);

	public int[] buy(Book... books);

}
