package library;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import library.model.Publisher;
import library.model.Book;
import library.service.PublisherService;
import library.service.BookService;

@Component
public class TestData {
	@Autowired
	private PublisherService publisherService;
	@Autowired
	private BookService bookService;

	
	@PostConstruct
	public void init() {
		Publisher laguna = new Publisher();
		laguna.setName("Laguna");
		publisherService.save(laguna);
		
		Book immortality = new Book();
		immortality.setTitle("Immortality");
		immortality.setPublisher(laguna);
		bookService.save(immortality);
		
		Publisher samizdat = new Publisher();
		samizdat.setName("Samizdat");
		publisherService.save(samizdat);
		
		Publisher geopolitika = new Publisher();
		geopolitika.setName("Geopolitika");
		publisherService.save(geopolitika);
		
		Book m2multiverzum = new Book();
		m2multiverzum.setTitle("M2 MULTIVERZUM");
		m2multiverzum.setPublisher(samizdat);
		bookService.save(m2multiverzum);
		
		Book noiseOfTime = new Book();
		noiseOfTime.setTitle("The noise of time");
		noiseOfTime.setPublisher(geopolitika);
		bookService.save(noiseOfTime);
	}
	
}
