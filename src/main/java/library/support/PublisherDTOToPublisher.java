package library.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import library.model.Publisher;
import library.service.PublisherService;
import library.web.dto.PublisherDTO;

@Component
public class PublisherDTOToPublisher implements Converter<PublisherDTO, Publisher>{
	
	@Autowired
	private PublisherService publisherService;

	@Override
	public Publisher convert(PublisherDTO dto) {
		Publisher publisher = null;

		if (dto.getId() != null) {
			publisher = publisherService.findOne(dto.getId());
		} else {
			publisher = new Publisher();
		}

		publisher.setName(dto.getName());
		publisher.setAddress(dto.getAddress());
		publisher.setPhone(dto.getPhone());
		
		return publisher;
	}
	
	public List<Publisher> convert(List<PublisherDTO> dtoList) {
		List<Publisher> ret = new ArrayList<>();

		for (PublisherDTO dto : dtoList) {
			ret.add(convert(dto));
		}

		return ret;
	}

}
