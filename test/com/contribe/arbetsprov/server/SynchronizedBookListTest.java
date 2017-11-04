package com.contribe.arbetsprov.server;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.contribe.arbetsprov.supplied.Book;
import com.contribe.arbetsprov.supplied.BookList;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SynchronizedBookListTest {
	
	@Autowired
	private SynchronizedBookList bookList;
	
	@Test
	public void checkSourceConnection() {
		
		assertEquals("checking basic loading",bookList.list("").length, 7);
	}
	
	@Test
	public void checkBasicSearch() {
		
		assertEquals("checking basic loading",bookList.list("a").length, 5);
	}
	
	@DirtiesContext
	@Test
	public void checkAddBuy() {
		
		Book book = new Book("title", "author", "10");
		
		bookList.add(book, 2);
		
		assertEquals("checking basic loading",bookList.list("").length, 8);
		
		int[] buy = bookList.buy(new Book[0]);
		
		assertEquals("checking buy array size", 0, buy.length);
		
		buy = bookList.buy(book);
		
		assertEquals("checking buy array size when 1 book is bought", 1, buy.length);
		
		assertEquals("checking buy array size when 1 book is bought [1/2]", BookList.OK, buy[0]);
		
		buy = bookList.buy(book);
		
		assertEquals("checking buy array size when 1 book is bought [2/2]", BookList.OK, buy[0]);
		
		buy = bookList.buy(book);
		
		assertEquals("checking buy array size when 1 book is bought [3/2], should fail", BookList.NOT_IN_STOCK, buy[0]);
		
		Book unknownBook = new Book("nope", "nope", "2");
		
		buy = bookList.buy(unknownBook);
		
		assertEquals("checking buy array when an unknown book is bought", BookList.DOES_NOT_EXIST, buy[0]);
		
	}
	
	
	
}
