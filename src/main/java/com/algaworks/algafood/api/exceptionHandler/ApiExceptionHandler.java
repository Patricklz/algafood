package com.algaworks.algafood.api.exceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	
	public static final String MSG_ERRO_GENERICO_USER = "Ocorreu um erro inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador.";
	
	@Autowired
	private MessageSource messageSource;
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		ApiErrorType apiErrorType = ApiErrorType.ERRO_DE_SISTEMA;
		
		String detail = MSG_ERRO_GENERICO_USER;
		
		ex.printStackTrace();
		
		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).userMessage(detail).build();
		
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		BindingResult bindingResult = ex.getBindingResult();
		

		
		return handleValidationInternal(ex, new HttpHeaders(), status, bindingResult, request);
		
		
	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request) {

		BindingResult bindingResult = ex.getBindingResult();
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		

		
		return handleValidationInternal(ex, new HttpHeaders(), status, bindingResult, request);


	}

	private ResponseEntity<Object>  handleValidationInternal(Exception ex, HttpHeaders headers, HttpStatus status,
			BindingResult bindingResult, WebRequest request) {
		
		
		ApiErrorType apiErrorType = ApiErrorType.DADOS_INVALIDOS;
		
		String detail = String.format("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.");
		
		String userMessage = String.format("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.");
		
		List<ApiError.Object> ApiErrorfields = bindingResult.getAllErrors().stream()
				.map(objectError -> {
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					
					String name = objectError.getObjectName();
					
					if(objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					
					return ApiError.Object.builder()				
						.name(name)
						.userMessage(message)
						.build();
	})
					.collect(Collectors.toList());
		
		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).userMessage(userMessage).objects(ApiErrorfields).build();
		
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
				
				
				
	}
	

	

	
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ApiErrorType apiErrorType = ApiErrorType.RECURSO_NAO_ENCONTRADO;
		
		String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.", ex.getRequestURL());
		
		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).userMessage(MSG_ERRO_GENERICO_USER).build();
		
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable rootCause = ex.getRootCause();

		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}

		if (rootCause instanceof PropertyBindingException) {

			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);

		}

		if( rootCause instanceof Exception) {
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}
		
		ApiErrorType apiErrorType = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";

		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).userMessage(MSG_ERRO_GENERICO_USER).build();

		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}
	
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiErrorType apiErrorType = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;


		
		String path = joinPath(ex.getPath());

		String detail = String.format("A propriedade '%s' não é uma propriedade válida para a requisição.", path);

		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).userMessage(MSG_ERRO_GENERICO_USER).build();

		return handleExceptionInternal(ex, apiError, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ApiErrorType apiErrorType = ApiErrorType.MENSAGEM_INCOMPREENSIVEL;

		String path = joinPath(ex.getPath());

		String detail = String.format(
				"A propriedade '%s' recebeu o valor '%s', "
						+ "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'.",
				path, ex.getValue(), ex.getTargetType().getSimpleName());
		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).userMessage(MSG_ERRO_GENERICO_USER).build();

		return handleExceptionInternal(ex, apiError, headers, status, request);
	}
	
	

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers, status,
					request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiErrorType apiErrorType = ApiErrorType.PARAMETRO_INVALIDO;

		String detail = String.format(
				"O parãmetro de URL '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo Long",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).userMessage(MSG_ERRO_GENERICO_USER).build();
		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
	}



	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
			WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ApiErrorType apiErrorType = ApiErrorType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();

		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).userMessage(MSG_ERRO_GENERICO_USER).build();

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

		ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail).userMessage(MSG_ERRO_GENERICO_USER).build();

		return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);

	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = ApiError.builder().title(status.getReasonPhrase()).status(status.value()).userMessage(MSG_ERRO_GENERICO_USER).build();
		} else if (body instanceof String) {
			body = ApiError.builder().title((String) body).status(status.value()).userMessage(MSG_ERRO_GENERICO_USER).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ApiErrorType apiErrorType,
			String detail) {
		
		return ApiError.builder().status(status.value()).type(apiErrorType.getUri()).title(apiErrorType.getTitle())
				.detail(detail).timestamp(LocalDateTime.now());
	}

	private String joinPath(List<Reference> references) {

		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

	}

}
