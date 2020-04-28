package library.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import library.model.Publisher;
import library.model.Book;
import library.service.PublisherService;
import library.service.BookService;
import library.web.dto.BookDTO;

@Component
public class BookDTOToBook implements Converter<BookDTO, Book>{
	
	@Autowired
	private BookService bookService;

	@Autowired
	private PublisherService publisherService;

	@Override
	public Book convert(BookDTO dto) {
		Publisher publisher = publisherService.findOne(dto.getPublisherId());

		if (publisher != null) {
			Book book = null;

			if (dto.getId() != null) {
				book = bookService.findOne(dto.getId());
			} else {
				book = new Book();
			}

			book.setTitle(dto.getTitle());

			book.setPublisher(publisher);

			return book;
		} else {
			throw new IllegalStateException("Trying to attach to non-existant entities");
		}
	}
	
	public List<Book> convert(List<BookDTO> dtoList) {
		List<Book> ret = new ArrayList<>();

		for (BookDTO bookDTO : dtoList) {
			ret.add(convert(bookDTO));
		}

		return ret;
	}

}
