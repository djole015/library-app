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
		laguna.setAddress("Resavska 33, Beograd");
		laguna.setPhone("011-334-1711");
		publisherService.save(laguna);
		
		Book immortality = new Book();
		immortality.setTitle("Immortality");
		immortality.setEdition(1990);
		immortality.setWriter("Milan Kundera");
		immortality.setIsbn("978-86-521-2679-8");
		immortality.setPublisher(laguna);
		bookService.save(immortality);
		
		Publisher samizdat = new Publisher();
		samizdat.setName("Samizdat");
		samizdat.setAddress("makedonska 30, Beograd");
		samizdat.setPhone("011-303-5407");
		publisherService.save(samizdat);
		
		Publisher geopolitika = new Publisher();
		geopolitika.setName("Geopolitika");
		geopolitika.setAddress("Gospodar jovanova 56, Beograd");
		geopolitika.setPhone("011-262-9707");
		publisherService.save(geopolitika);
		
		Book m2multiverzum = new Book();
		m2multiverzum.setTitle("M2 MULTIVERZUM");
		m2multiverzum.setEdition(2018);
		m2multiverzum.setWriter("Ivan Tokin");
		m2multiverzum.setIsbn("978-86-7963-478-8");
		m2multiverzum.setPublisher(samizdat);
		bookService.save(m2multiverzum);
		
		Book noiseOfTime = new Book();
		noiseOfTime.setTitle("The noise of time");
		noiseOfTime.setEdition(2017);
		noiseOfTime.setWriter("Julian Barnes");
		noiseOfTime.setIsbn("978-86-6145-221-5");
		noiseOfTime.setPublisher(geopolitika);
		bookService.save(noiseOfTime);
	}
	
}
