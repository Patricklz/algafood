package com.algaworks.algafood.api.exceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ex.getRootCause();
		
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException)rootCause, headers, status, request);
		}
		
		if (rootCause instanceof PropertyBindingException) {
			
			return handlePropertyBindingException((PropertyBindingException)rootCause, headers, status, request);
			
		}
		
		ApiErrorType apiErrorType = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).build();

		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiErrorType apiErrorType = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;
		
		String path = joinPath(ex.getPath());
		
		String detail = String.format("A propriedade '%s' não é uma propriedade válida para a requisição.", path);
		
		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).build();
		
		return handleExceptionInternal(ex, apiError, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ApiErrorType apiErrorType = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;
		
		String path = joinPath(ex.getPath());
		
		
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', " + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'.", 
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).build();
		

		return handleExceptionInternal(ex, apiError, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
			WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ApiErrorType apiErrorType = ApiErrorType.ENTIDADE_NAO_ENCONTRADA;
		String detail = ex.getMessage();

		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).build();
	
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);

	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {

		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;
		ApiErrorType apiErrorType = ApiErrorType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();

		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).build();

		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);

	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = ApiError.builder().title(status.getReasonPhrase()).status(status.value()).build();
		} else if (body instanceof String) {
			body = ApiError.builder().title((String) body).status(status.value()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ApiErrorType apiErrorType,
			String detail) {
		return ApiError.builder().status(status.value()).type(apiErrorType.getUri()).title(apiErrorType.getTitle())
				.detail(detail);
	}
	
	private String joinPath(List<Reference> references) {
		
		return references.stream()
		.map(ref -> ref.getFieldName())
		.collect(Collectors.joining("."));
		
		
	}

}
