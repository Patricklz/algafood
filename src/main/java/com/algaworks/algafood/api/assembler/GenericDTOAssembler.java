package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;

/**
 * A generic Model Assembler that uses ModelMapper.
 * 
 * @param <T> DTO type to assemble from.
 * @param <S> Domain type to assemble to.
 */

@Component
public class GenericDTOAssembler<T, S> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public S toDTO(T domainObject, Class<S> type) {
		return modelMapper.map(domainObject, type);
	}

	
	public List<S> toCollectDTO(List<T> objects, Class<S> type) {
		
		return objects.stream().map(object -> toDTO(object, type)).collect(Collectors.toList());
	}
}
