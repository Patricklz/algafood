package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.DTO.FotoProdutoDTO;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;

@RestController

@RequestMapping(value = "/restaurantes/{restauranteId}/{produtoId}/foto")
public class RestauranteProdutoFotoController {
	
	
	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		MultipartFile arquivo = fotoProdutoInput.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProdutoInput.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSave = catalogoFotoProdutoService.salvar(foto,  arquivo.getInputStream());

		return fotoProdutoModelAssembler.toDTO(fotoSave);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDTO buscar (@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		return fotoProdutoModelAssembler.toDTO(fotoProduto);
	}

	@GetMapping
	public ResponseEntity<InputStreamResource> servirFoto (@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		
		try {
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
		
		MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
		
		List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
		
		verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
		
		InputStream InputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
		
		return ResponseEntity.ok().contentType(mediaTypeFoto).body(new InputStreamResource(InputStream));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	private void verificarCompatibilidadeMediaType( MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
		boolean compativel = mediaTypesAceitas.stream().anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		
		if(!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
	}
	
	@DeleteMapping
	public void remover (@PathVariable Long restauranteId, @PathVariable Long produtoId ) {
		
		catalogoFotoProdutoService.excluir(restauranteId, produtoId);
		
	}
	
}
