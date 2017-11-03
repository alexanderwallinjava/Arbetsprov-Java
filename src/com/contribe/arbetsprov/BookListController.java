package com.contribe.arbetsprov;


import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class BookListController {

	Logger logger = LoggerFactory.getLogger(BookListController.class);
	
	@Autowired
	SynchronizedBookList books;
	
	@RequestMapping(value="/search", method = RequestMethod.POST)
	@ResponseBody
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
