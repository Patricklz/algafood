package com.algaworks.algafood.api.model.DTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {
	

	private Long id;

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
	
	private FormaPagamentoDTO formaPagamento;

	private EnderecoDTO enderecoEntrega;

    private List<ItemPedidoDTO> itens = new ArrayList<>();
	

}
