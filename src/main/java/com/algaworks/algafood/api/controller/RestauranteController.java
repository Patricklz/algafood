package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.DTO.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteDTOInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDissembler;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	

	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public List<RestauranteDTO> listar() {
		return restauranteModelAssembler.toCollectDTO(restauranteRepository.findAll());
	}

	@GetMapping("/{id}")
	public RestauranteDTO buscar(@PathVariable Long id) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);
		
		return restauranteModelAssembler.toDTO(restaurante);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteDTOInput restauranteDTOInput) {
		try {
			
			Restaurante restaurante = restauranteInputDissembler.dtoToModel(restauranteDTOInput);
			
			return restauranteModelAssembler.toDTO(cadastroRestauranteService.salvar(restaurante));
		}catch(CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}
	

	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteDTOInput restauranteDTOInput) {
		try {
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
				

			restauranteInputDissembler.copyDtoToModel(restauranteDTOInput, restauranteAtual);
			restauranteAtual = cadastroRestauranteService.salvar(restauranteAtual);
			
			return restauranteModelAssembler.toDTO(restauranteAtual);
			}catch(CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
				throw new NegocioException(e.getMessage(), e);
			}
			 
		
	}
	
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		cadastroRestauranteService.ativar(id);
	}
	
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		cadastroRestauranteService.inativar(id);
	}
	
	@PutMapping("/{id}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long id) {
		cadastroRestauranteService.fechar(id);
	}
	
	@PutMapping("/{id}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long id) {
		cadastroRestauranteService.abrir(id);
	}
	
//	@PatchMapping("/{id}")
//	public RestauranteDTO atualizacaoParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
//		
//		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
//		 
//		merge(campos, restauranteAtual, request);
//		
//		validate(restauranteAtual, "restaurante");
//		
//		return atualizar(id, restauranteDTOAssembler.toDTO(restauranteAtual));
//	}
//
//	
//	private void validate(Restaurante restaurante, String objectName) {
//		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
//		
//		validator.validate(restaurante, bindingResult);
//		
//		if (bindingResult.hasErrors()) {
//			throw new ValidacaoException(bindingResult);
//		}
//		
//	}
//
//	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
//		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//		
//		try {
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//		
//		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
//		
//		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//			field.setAccessible(true);
//			
//			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//			
//			ReflectionUtils.setField(field, restauranteDestino, novoValor);
//		});
//		} catch (IllegalArgumentException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//		}
//	}
	
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
			cadastroRestauranteService.excluir(id);
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try {
		cadastroRestauranteService.ativar(restauranteIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restauranteIds) {
		try{
		cadastroRestauranteService.inativar(restauranteIds);
	} catch (RestauranteNaoEncontradoException e) {
		throw new NegocioException(e.getMessage(), e);
	}

	}
		
	
}
