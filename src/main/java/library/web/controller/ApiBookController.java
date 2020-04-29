package library.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import library.model.Book;
import library.service.BookService;
import library.support.BookDTOToBook;
import library.support.BookToBookDTO;
import library.web.dto.BookDTO;

@RestController
@RequestMapping(value="/api/books")
public class ApiBookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookToBookDTO toDTO;

	@Autowired
	private BookDTOToBook toBook;
	
	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<List<BookDTO>> getBooks(
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String writer,
			@RequestParam(required = false) Integer minVotesCount,
			@RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {

		Page<Book> booksPage;

		if (title != null ) {
			booksPage = bookService.search(title, writer, minVotesCount, pageNum);
		} else {
			booksPage = bookService.findAll(pageNum);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("totalPages", Integer.toString(booksPage.getTotalPages()));

		return new ResponseEntity<>(toDTO.convert(booksPage.getContent()), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<BookDTO> getBook(@PathVariable Long id) {
		Book book = bookService.findOne(id);
		if (book == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toDTO.convert(book), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	ResponseEntity<BookDTO> delete(@PathVariable Long id) {
		Book deleted = bookService.delete(id);

		if (deleted == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toDTO.convert(deleted), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<BookDTO> add(@Validated @RequestBody BookDTO newBookDTO) {

		Book savedBook = bookService.save(toBook.convert(newBookDTO));

		return new ResponseEntity<>(toDTO.convert(savedBook), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	public ResponseEntity<BookDTO> edit(@Validated @RequestBody BookDTO bookDTO, @PathVariable Long id) {

		if (!id.equals(bookDTO.getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Book persisted = bookService.save(toBook.convert(bookDTO));

		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.OK);
	}

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<Void> handle() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
