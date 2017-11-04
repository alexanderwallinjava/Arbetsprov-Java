package com.contribe.arbetsprov;


import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.contribe.arbetsprov.payload.AddPayload;



@RestController
public class BookListController {

	Logger logger = LoggerFactory.getLogger(BookListController.class);
	
	@Autowired
	SynchronizedBookList books;
	
	@RequestMapping(value="/search", method = RequestMethod.POST)
	public Book[] search(@RequestBody String request) {
		
		try {
			JSONObject json = new JSONObject(request);
			
			if(!json.has("search")) {
				logger.info("request for search; no parameters");
				return books.list("");
			}
			
			String search = json.getString("search");
			logger.info("request for search; parameter: " + search);
			
			
			return books.list(search);
		} catch (JSONException e) {
			return new Book[0];
		}
		
		
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public boolean add(@RequestBody AddPayload payload) {
		logger.info("request for add; "+ payload);
		
		if(!payload.book.allVariablesSet())
			return false;
		
		return books.add(payload.book, payload.amount);
	}
	
	@RequestMapping(value="/buy", method = RequestMethod.POST)
	public int[] buy(@RequestBody Book[] books) {
		
		return this.books.buy(books);
	}
}
