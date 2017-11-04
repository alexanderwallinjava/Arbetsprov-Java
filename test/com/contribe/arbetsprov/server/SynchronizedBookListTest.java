package com.contribe.arbetsprov.server;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.contribe.arbetsprov.supplied.Book;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SynchronizedBookListTest {

	@Autowired
	private SynchronizedBookList bookList;
	
	@DirtiesContext
	@Test
	public void checkAdd2() {
		
		bookList.add(new Book("title", "author", "10"), 2);
		
		assertEquals("checking basic loading",bookList.list("").length, 8);
	}
	
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
	public void checkAdd() {
		
		bookList.add(new Book("title", "author", "10"), 2);
		
		assertEquals("checking basic loading",bookList.list("").length, 8);
	}
	
}
