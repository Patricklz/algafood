package com.algaworks.algafood.api.assembler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.PedidoDTOInput;
import com.algaworks.algafood.api.model.input.ProdutoDTOInput;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class PedidoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Pedido DTOToModel(PedidoDTOInput pedidoDTOInput) {
		return modelMapper.map(pedidoDTOInput, Pedido.class);
	}
	
	public void copyDTOToModel(PedidoDTOInput pedidoDTOInput, Pedido pedido) {

		modelMapper.map(pedidoDTOInput, pedido);
	}
	

	
	

}
