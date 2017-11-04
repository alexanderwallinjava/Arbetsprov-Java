package com.contribe.arbetsprov.server;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.contribe.arbetsprov.client.RESTClientBookList;
import com.contribe.arbetsprov.supplied.Book;
import com.contribe.arbetsprov.supplied.BookList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRest {
	
	@Test
	public void testBasicConnect() {
		RESTClientBookList clientAPI = new RESTClientBookList();
		
		assertEquals("checking if search returns 7 as default", 7, clientAPI.list("").length);
		
		assertEquals("checking if search returns 5 when searching for 'a' as default", 5, clientAPI.list("a").length);
	}
	
	@DirtiesContext
	@Test
	public void testAddBuy() {
		RESTClientBookList clientAPI = new RESTClientBookList();
		
		assertEquals("checking if search returns 7 as default", 7, clientAPI.list("").length);
		
		Book book = new Book("temp temp", "auth auth", "2");
		
		clientAPI.add(book, 2);
		
		assertEquals("checking if search returns 8 when added one", 8, clientAPI.list("").length);
		
		int[] buy = clientAPI.buy(book);
	
		assertEquals("checking buy array size when 1 book is bought", 1, buy.length);
		
		assertEquals("checking buy array size when 1 book is bought [1/2]", BookList.OK, buy[0]);
		
		buy = clientAPI.buy(book);
		
		assertEquals("checking buy array size when 1 book is bought [2/2]", BookList.OK, buy[0]);
		
		buy = clientAPI.buy(book);
		
		assertEquals("checking buy array size when 1 book is bought [3/2], should fail", BookList.NOT_IN_STOCK, buy[0]);
	}

}
