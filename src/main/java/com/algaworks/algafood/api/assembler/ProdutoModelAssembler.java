package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.ProdutoDTO;
import com.algaworks.algafood.api.model.DTO.UsuarioDTO;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class ProdutoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoDTO toDTO(Produto produto) {
		
		return modelMapper.map(produto, ProdutoDTO.class);
		
	}
	
	public List<ProdutoDTO> toCollectDTO(List<Produto> produtos) {
		
		return produtos.stream().map(produto -> modelMapper.map(produto, ProdutoDTO.class)).collect(Collectors.toList());
	}

}
