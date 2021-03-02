package com.algaworks.algafood.domain.model.DTO;

import java.math.BigDecimal;
import java.util.Date;

import com.algaworks.algafood.domain.model.Restaurante;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class VendaDiaria {

	private Date data;
	private Long totalVendas;
	private BigDecimal totalFaturado;
}
