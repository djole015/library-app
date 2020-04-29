package library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import library.model.Book;
import library.repository.BookRepository;
import library.service.BookService;

@Service
public class JpaBookService implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Page<Book> findAll(int pageNum) {
		return bookRepository.findAll(new PageRequest(pageNum, 5));
	}

	@Override
	public Book findOne(Long id) {
		return bookRepository.findOne(id);
	}

	@Override
	public Book save(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public Book delete(Long id) {
		Book book = bookRepository.findOne(id);
		if (book != null) {
			bookRepository.delete(id);
		}

		return book;
	}

	@Override
	public Page<Book> search(String naziv, String writer, Integer minVotesCount, int pageNum) {
		return bookRepository.search(naziv, writer, minVotesCount, new PageRequest(pageNum, 5));
	}

	@Override
	public List<Book> findByPublisherId(Long publisherId) {
		return bookRepository. findByPublisherId(publisherId);
	}

}
