package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A generic Model Assembler that uses ModelMapper.
 * 
 * @param <T> DTO type to Dissemble from.
 * @param <S> Domain type to Dissemble to.
 */

@Component
public class GenericDTODissembler<T, S> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public S dtoToModel(T inputOrigin, Class<S> type) {
		return modelMapper.map(inputOrigin, type);
	}
	
	
	public void copydtoToModel(T inputOrigin, Class<S> type) {
		//restaurante.setCozinha(new Cozinha());
		
		//manager.detach(restaurante.getCozinha());
		
		modelMapper.map(inputOrigin, type);
	}


}
