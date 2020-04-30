package library.service;

import java.util.List;

import org.springframework.data.domain.Page;

import library.model.Book;

public interface BookService {

	Page<Book> findAll(int pageNum);

	Book findOne(Long id);
	
	Book save(Book book);

	Book delete(Long id);
	
	Book vote(Long id);

	Page<Book> search(String naziv, String writer, Integer minVotesCount, int pageNum);

	List<Book> findByPublisherId(Long publisherId);

}
