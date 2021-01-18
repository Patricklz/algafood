package com.algaworks.algafood.api.assembler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.ProdutoDTO;
import com.algaworks.algafood.api.model.DTO.UsuarioDTO;
import com.algaworks.algafood.api.model.input.ProdutoDTOInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class ProdutoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Produto DTOToModel(ProdutoDTOInput produtoDTOInput) {
		return modelMapper.map(produtoDTOInput, Produto.class);
	}
	
	public void copyDTOToModel(ProdutoDTOInput produtoDTOInput, Produto produto) {

		modelMapper.map(produtoDTOInput, produto);
	}
	

	
	

}
