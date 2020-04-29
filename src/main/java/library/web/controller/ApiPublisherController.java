package library.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import library.model.Book;
import library.model.Publisher;
import library.service.BookService;
import library.service.PublisherService;
import library.support.BookToBookDTO;
import library.support.PublisherToPublisherDTO;
import library.web.dto.BookDTO;
import library.web.dto.PublisherDTO;

@RestController
@RequestMapping(value = "/api/publishers")
public class ApiPublisherController {
	@Autowired
	private PublisherService publisherService;
	@Autowired
	private BookService bookService;
	@Autowired
	private PublisherToPublisherDTO toDTO;
	@Autowired
	private BookToBookDTO toBookDTO;

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<List<PublisherDTO>> getPublishers() {

		List<Publisher> publishers = null;

		publishers = publisherService.findAll();

		return new ResponseEntity<>(toDTO.convert(publishers), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<PublisherDTO> get(@PathVariable Long id) {

		Publisher publisher = publisherService.findOne(id);

		if (publisher == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toDTO.convert(publisher), HttpStatus.OK);
	}

	@RequestMapping(value = "/{publisherId}/books", method = RequestMethod.GET)
	public ResponseEntity<List<BookDTO>> getBooks(@PathVariable Long publisherId) {

		List<Book> books = bookService.findByPublisherId(publisherId);

		if (books == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toBookDTO.convert(books), HttpStatus.OK);
	}

}
