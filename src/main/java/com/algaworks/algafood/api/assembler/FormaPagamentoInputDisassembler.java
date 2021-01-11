package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CozinhaDTOInput;
import com.algaworks.algafood.api.model.input.FormaPagamentoDTOInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public FormaPagamento dtoToModel(FormaPagamentoDTOInput formaPagamentoDTOInput) {
		return modelMapper.map(formaPagamentoDTOInput, FormaPagamento.class);
	}
	
	public void copyDTOToModel(FormaPagamentoDTOInput formaPagamentoDTOInput, FormaPagamento formaPagamento) {
		
		modelMapper.map(formaPagamentoDTOInput, formaPagamento);
	}
	
	

}
