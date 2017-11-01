package com.contribe.arbetsprov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookListController {

	@Autowired
	SynchronizedBookList books;
	
	@RequestMapping("/search")
	public Book[] search(@RequestParam(value="search", defaultValue="") String search) {
		return new Book[0]; //books.list(search);
	}
	
}
