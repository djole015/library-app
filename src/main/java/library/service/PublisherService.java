package library.service;

import java.util.List;

import library.model.Publisher;

public interface PublisherService {

	List<Publisher> findAll();

	Publisher save(Publisher publisher);

	Publisher findOne(Long id);
}
