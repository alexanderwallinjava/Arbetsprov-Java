package com.contribe.arbetsprov.supplied;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookTest {

	@Test
	public void basicBookTest() {
		Book book = new Book("title", "author", "2");
		
		assertEquals("checking title", "title", book.getTitle());
		
		assertEquals("checking author", "author", book.getAuthor());
		
		assertEquals("checking price", "2", book.getPrice().toString());
	}
	
	@Test
	public void checkNull() {
		Book book = new Book("title", null, "2");
		
		assertEquals("checking if null is detected", false, book.allVariablesSet());
		
		Book bookNormal = new Book("title", "author", "2");
		
		assertEquals("checking if null is detected", false, book.allVariablesSet());
		
		assertEquals("checking if null is detected", true, bookNormal.allVariablesSet());
	}
	
}
