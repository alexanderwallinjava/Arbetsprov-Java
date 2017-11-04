package com.contribe.arbetsprov.client;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.TreeSet;

import org.springframework.web.client.RestClientException;

import com.contribe.arbetsprov.supplied.Book;
import com.contribe.arbetsprov.supplied.BookList;

import ch.qos.logback.classic.Level;

/**
 * A possible implementation for a client using command line access through REST
 */
public class ClientMain {
	
	public static void main(String[] args) throws RestClientException, UnsupportedEncodingException {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.OFF);
		
		BookList api = new RESTClientBookList();
		
		TreeSet<Book> selectedBooks = new TreeSet<>();
		
		Scanner in = new Scanner(System.in);
		
		while (true) {
			System.out.println();
			if(selectedBooks.size() > 0) {
				System.out.println("selected books:");
				for(Book book : selectedBooks)
					System.out.println("\t" + book);
				System.out.println();
			}
			System.out.println("Please choose command");
			System.out.println("1. search for books");
			System.out.println("2. add book");
			System.out.println("3. buy selection");
			System.out.println("4. clear selection");
			System.out.println("5. exit");
			
			String line = in.nextLine().trim();
			
			if(!line.matches("\\d+")) {
				System.out.println("incorrect selection");
				continue;
			}
			int choice = Integer.parseInt(line);
			
			if(choice == 1) {
				System.out.println("please enter search phrase");
				String search = in.nextLine().trim();
				
				Book[] books = api.list(search);
				while(true) {
					
					if(selectedBooks.size() > 0) {
						System.out.println("selected books:");
						for(Book book : selectedBooks)
							System.out.println("\t" + book);
						System.out.println();
					}
					
					for(int i = 0; i < books.length; i++)
						System.out.println((i+1) + "\t" + books[i]);
					System.out.println();
					System.out.println("please make selection");
					System.out.println("1. select a book");
					System.out.println("2. return to previous menu");
					
					line = in.nextLine().trim();
					
					if(!line.matches("\\d+")) {
						System.out.println("incorrect selection");
						continue;
					}
					choice = Integer.parseInt(line);
					
					if(choice == 1) {
						System.out.println("please enter id of book");
						
						line = in.nextLine().trim();
						
						if(!line.matches("\\d+")) {
							System.out.println("incorrect selection");
							continue;
						}
						choice = Integer.parseInt(line);
						
						choice -= 1;
						
						if(choice >= 0 && choice < books.length) {
							if(selectedBooks.contains(books[choice]))
								selectedBooks.remove(books[choice]);
							else
								selectedBooks.add(books[choice]);
						}
						
					} else if(choice == 2) {
						break;
					} else {
						System.out.println("incorrect selection");
					}
				}	
			} else if(choice == 2) {
				System.out.println("please enter title");
				String title = in.nextLine().trim();
				System.out.println("please enter author");
				String author = in.nextLine().trim();
				System.out.println("please enter price");
				String price = in.nextLine().trim();
				System.out.println("please enter number of books");
				String amount = in.nextLine().trim();
				
				if(!amount.matches("\\d+")) {
					System.out.println("number of books must be a number");
					continue;
				}
				try {
					boolean success = api.add(new Book(title, author, price), Integer.parseInt(amount));
					
					if(success)
						System.out.println("book was added successfully");
					else
						System.out.println("unable to add book");
				} catch(Exception E) {
					System.out.println("unable to send book to server; some of the supplied data was malformed");
				}
				
			} else if(choice == 3) {
				if(selectedBooks.size() > 0) {
					Book[] books = selectedBooks.toArray(selectedBooks.toArray(new Book[selectedBooks.size()]));
					int[] success = api.buy(books);
					
					System.out.println("\nresults:");
					for(int i = 0; i < Math.min(books.length, success.length); i++) {
						if(success[i] == BookList.OK)
							System.out.print("[BOUGHT]\t");
						else if(success[i] == BookList.NOT_IN_STOCK)
							System.out.print("[NOT IN STOCK]\t");
						else if(success[i] == BookList.DOES_NOT_EXIST)
							System.out.print("[DOES NOT EXIST]\t");
						System.out.println(books[i]);
						System.out.println();
					}
					
				} else {
					System.out.println("no books selected; please make a selection");
				}
			} else if(choice == 4) {
				selectedBooks.clear();
			}else if(choice == 5) {
				System.out.println("exiting");
				break;
			} else {
				System.out.println("incorrect selection");
			}
			
		}
		in.close();
		System.out.println("bye!");
	}

}
