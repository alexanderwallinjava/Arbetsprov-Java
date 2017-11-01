package com.contribe.arbetsprov;

import java.io.Serializable;
import java.math.BigDecimal;

public class Book implements Serializable, Comparable<Book> {
	
	private static final long serialVersionUID = 1373288047646590681L;

	private String title;
	private String author;
	private BigDecimal price;
	
	public Book(String title, String author, String price) {
		this.title = title;
		this.author = author;
		this.price = new BigDecimal(price);
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getAuthor() {
		return this.author;
	}

	public BigDecimal getPrice() {
		return price.add(BigDecimal.ZERO);
	}

	@Override
	public int compareTo(Book o) {
		if(!this.author.equals(o.author))
			return this.author.compareTo(o.author);
		else if(!this.title.equals(o.title))
			return this.title.compareTo(o.title);
		
		return price.compareTo(o.price);
	}
	
	@Override
	public boolean equals(Object o) {
		try {
			return this.compareTo((Book)o)==0;
		} catch(ClassCastException E) {}
		return false;
	}
	
	public boolean matchesIgnoreCase(String subString) {
		if(subString.length() == 0)
			return true;
		
		if(this.author.toLowerCase().contains(subString.toLowerCase()))
			return true;
		else if(this.title.toLowerCase().contains(subString.toLowerCase()))
			return true;
		
		return false;
	}
}