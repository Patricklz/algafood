package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FileSize;
import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoInput {

	@FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	@FileSize(max = "500KB")
	@NotNull
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
}
