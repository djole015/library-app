package library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Book {

	@Id
	@GeneratedValue
	@Column
	private Long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private Integer edition;
	@Column(nullable = false)
	private String writer;
	@Column(nullable = false, unique = true)
	private String isbn;
	@Column
	private Integer bookCount;
	@Column
	private Integer votesCount = 0;

	@ManyToOne(fetch = FetchType.EAGER)
	private Publisher publisher;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getEdition() {
		return edition;
	}

	public void setEdition(Integer edition) {
		this.edition = edition;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getBookCount() {
		return bookCount;
	}

	public void setBookCount(Integer bookCount) {
		this.bookCount = bookCount;
	}

	public Integer getVotesCount() {
		return votesCount;
	}

	public void setVotesCount(Integer votesCount) {
		this.votesCount = votesCount;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
		if (publisher != null && !publisher.getBooks().contains(this)) {
			publisher.getBooks().add(this);
		}
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", edition=" + edition + ", writer=" + writer + ", isbn=" + isbn
				+ ", votesCount=" + votesCount + ", publisher=" + publisher + "]";
	}

}
