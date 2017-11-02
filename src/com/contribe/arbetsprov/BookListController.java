package com.contribe.arbetsprov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
		return new Book[0]; //books.list(search);
	}
	
}
