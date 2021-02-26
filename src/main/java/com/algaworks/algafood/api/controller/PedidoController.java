package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.DTO.PedidoDTO;
import com.algaworks.algafood.api.model.DTO.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoDTOInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	
	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//		List<Pedido> todosPedidos = pedidoRepository.findAll();
//		List<PedidoResumoDTO> pedidoResumoDTO = pedidoResumoModelAssembler.toCollectDTO(todosPedidos);
//		
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidoResumoDTO);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		
//		
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());	
//		
//		
//		if(StringUtils.isNotBlank((campos))) {
//			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.trim().split(",")));
//		}
//		
//		pedidosWrapper.setFilters(filterProvider);
//		
//		return pedidosWrapper;
//	}	
	
	
//	@GetMapping
//	public List<PedidoResumoDTO> listar() {
//		List<Pedido> todosPedidos = pedidoRepository.findAll();
//		
//		return pedidoResumoModelAssembler.toCollectDTO(todosPedidos);
//	}
	
	@GetMapping
	public List<PedidoResumoDTO> pesquisar(PedidoFilter filtro) {
		List<Pedido> todosPedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));
		
		return pedidoResumoModelAssembler.toCollectDTO(todosPedidos);
	}
	
	@GetMapping("/{codigoPedido}")
	public PedidoDTO buscar(@PathVariable String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
		
		return pedidoModelAssembler.toDTO(pedido);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@Valid @RequestBody PedidoDTOInput pedidoDTOInput) {
	    try {
	        Pedido novoPedido = pedidoInputDisassembler.DTOToModel(pedidoDTOInput);

	        // TODO pegar usuário autenticado
	        novoPedido.setCliente(new Usuario());
	        novoPedido.getCliente().setId(1L);

	        novoPedido = emissaoPedidoService.emitir(novoPedido);

	        return pedidoModelAssembler.toDTO(novoPedido);
	    } catch (EntidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}

}
