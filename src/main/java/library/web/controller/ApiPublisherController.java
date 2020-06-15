package library.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import library.model.Book;
import library.model.Publisher;
import library.service.BookService;
import library.service.PublisherService;
import library.support.BookToBookDTO;
import library.support.PublisherDTOToPublisher;
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
	private PublisherDTOToPublisher toPublisher;
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
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<PublisherDTO> add(@RequestBody PublisherDTO newPublisherDTO) {

		Publisher savedPublisher = publisherService.save(toPublisher.convert(newPublisherDTO));

		return new ResponseEntity<>(toDTO.convert(savedPublisher), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{publisherId}/books", method = RequestMethod.GET)
	public ResponseEntity<List<BookDTO>> getBooks(@PathVariable Long publisherId) {

		List<Book> books = bookService.findByPublisherId(publisherId);

		if (books == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toBookDTO.convert(books), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	public ResponseEntity<PublisherDTO> edit(@RequestBody PublisherDTO publisherDTO, @PathVariable Long id) {

		if (!id.equals(publisherDTO.getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Publisher persisted = publisherService.save(toPublisher.convert(publisherDTO));

		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	ResponseEntity<PublisherDTO> delete(@PathVariable Long id) {
		Publisher deleted = publisherService.findOne(id);

		if (deleted == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		publisherService.delete(id);
		
		return new ResponseEntity<>(toDTO.convert(deleted), HttpStatus.OK);
	}

}
