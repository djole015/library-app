package library.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import library.model.Book;
import library.web.dto.BookDTO;

@Component
public class BookToBookDTO implements Converter<Book, BookDTO>{

	@Override
	public BookDTO convert(Book source) {
		BookDTO dto = new BookDTO();

		dto.setId(source.getId());
		dto.setTitle(source.getTitle());
		dto.setEdition(source.getEdition());
		dto.setWriter(source.getWriter());
		dto.setIsbn(source.getIsbn());
		dto.setBookCount(source.getBookCount());
		dto.setVotesCount(source.getVotesCount());

		dto.setPublisherId(source.getPublisher().getId());
		dto.setPublisherName(source.getPublisher().getName());

		return dto;
	}
	
	public List<BookDTO> convert(List<Book> sourceList) {
		List<BookDTO> ret = new ArrayList<>();

		for (Book k : sourceList) {
			ret.add(convert(k));
		}

		return ret;
	}

}
