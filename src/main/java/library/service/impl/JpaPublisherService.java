package library.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import library.model.Publisher;
import library.repository.PublisherRepository;
import library.service.PublisherService;

@Service
public class JpaPublisherService implements PublisherService{

	@Autowired
	private PublisherRepository publisherRepository;
	
	@Override
	public List<Publisher> findAll() {
		return publisherRepository.findAll();
	}

	@Override
	public Publisher save(Publisher publisher) {
		return publisherRepository.save(publisher);
	}

	@Override
	public Publisher findOne(Long id) {
		return publisherRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		publisherRepository.delete(id);;
	}

}
