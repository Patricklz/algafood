package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.EstadoDTOInput;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Estado DTOToModel(EstadoDTOInput estadoDTOInput) {
		return modelMapper.map(estadoDTOInput, Estado.class);
	}
	
	public void copyDTOToModel(EstadoDTOInput estadoDTOInput, Estado estado) {
		modelMapper.map(estadoDTOInput, estado);
	}
	
	

}
