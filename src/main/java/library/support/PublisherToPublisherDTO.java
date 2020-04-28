package library.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import library.model.Publisher;
import library.web.dto.PublisherDTO;

@Component
public class PublisherToPublisherDTO implements Converter<Publisher, PublisherDTO>{

	@Override
	public PublisherDTO convert(Publisher source) {
		PublisherDTO dto = new PublisherDTO();

		dto.setId(source.getId());
		dto.setName(source.getName());

		return dto;
	}
	
	public List<PublisherDTO> convert(List<Publisher> sourceList) {
		List<PublisherDTO> ret = new ArrayList<>();

		for (Publisher source : sourceList) {
			ret.add(convert(source));
		}

		return ret;
	}


}
