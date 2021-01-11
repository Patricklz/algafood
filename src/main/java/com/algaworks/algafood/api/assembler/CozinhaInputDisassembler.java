package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CozinhaDTOInput;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public Cozinha dtoToModel(CozinhaDTOInput cozinhaDTOInput) {
		return modelMapper.map(cozinhaDTOInput, Cozinha.class);
	}
	
	public void copyDTOToModel(CozinhaDTOInput cozinhaDTOInput, Cozinha cozinha) {
		
		modelMapper.map(cozinhaDTOInput, cozinha);
	}
	
	

}
