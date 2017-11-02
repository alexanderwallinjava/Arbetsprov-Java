package com.contribe.arbetsprov;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookListController {

	Logger logger = LoggerFactory.getLogger(BookListController.class);
	
	@Autowired
	SynchronizedBookList books;
	
	@RequestMapping("/search")
	public Book[] search(@RequestParam(value="search", defaultValue="") String search) {
		logger.info("request for search; parameter: " + search);
		return books.list(search);
	}
	
	@RequestMapping("/add")
	public boolean add(Book book, @RequestParam(value="amount") int amount) {
		logger.info("request for add; amount:" + amount + ", book:" + book.toString());
		
		//check if Book has been correctly initialized
		if(!book.allVariablesSet())
			return false;
		
		return books.add(book, amount);
	}
	
	@RequestMapping("/buy")
	public int[] buy(@RequestParam MultiValueMap<String, String> data) {
		
		System.out.println(data);
		
		int numBooks = Math.min(Math.min(data.get("title").size(),data.get("author").size()), data.get("price").size());
		
		Book[] books = new Book[numBooks];
		
		for(int i = 0; i < numBooks; i++) {
			books[i] = new Book(
					data.get("title").get(i),
					data.get("author").get(i),
					data.get("price").get(i));
		}
		
		return this.books.buy(books);
	}
}
