package library.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import library.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

	@Query("SELECT b FROM Book b WHERE "
			+ "(:title IS NULL OR b.title = :title) AND "
			+ "(:writer IS NULL OR b.writer = :writer) AND "
			+ "(b.votesCount >= :minVotesCount)"
			)
	Page<Book> search(
			@Param("title") String title, 
			@Param("writer") String writer,
			@Param("minVotesCount") Integer minVotesCount,
			Pageable pageRequest
			);

	List<Book> findByPublisherId(Long publisherId);

}
