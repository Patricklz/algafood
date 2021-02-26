package com.algaworks.algafood.api.model.DTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algafood.domain.model.StatusPedido;
import com.fasterxml.jackson.annotation.JsonFilter;

import lombok.Getter;
import lombok.Setter;

//@JsonFilter("pedidoFilter")
@Getter
@Setter
public class PedidoResumoDTO {
	

	private String codigo;

	private BigDecimal subtotal;

	private BigDecimal taxaFrete;

	private BigDecimal valorTotal;

	private StatusPedido status;

	private OffsetDateTime dataCriacao;
	
	private OffsetDateTime dataConfirmacao;
	
	private OffsetDateTime dataCancelamento;	
	
	private OffsetDateTime dataEntrega;
	
	private RestauranteResumidoDTO restaurante;
	
	private UsuarioDTO cliente;

	

}
