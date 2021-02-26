package com.algaworks.algafood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.algaworks.algafood.api.model.DTO.ItemPedidoDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTOInput {
	
	@Valid
	@NotNull
	private RestauranteIdDTOInput restaurante;
	
	@Valid
	@NotNull
	private FormaPagamentoIdDTOInput formaPagamento;
	
	@Valid
	@NotNull
	private EnderecoDTOInput enderecoEntrega;
	
	@Valid
	@Size(min = 1)
	@NotNull
	private List<ItemPedidoDTOInput> itens;

	

}
