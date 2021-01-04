package com.algaworks.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroRestauranteApiIT {
	

    private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

	private static final int RESTAURANTE_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	
	private Restaurante restauranteThaiGoumet;
	private Cozinha cozinhaThaiGoumet;
	private Restaurante restauranteThaiDelivery;
	private Cozinha cozinhaThaiDelivery;
	private int quantidadeRestaurantesCadastrados;
	private String jsonRestaurante;
	private String jsonRestauranteSemFrete;
	private String jsonRestauranteSemCozinha;
	private String jsonRestauranteComCozinhaInexistente;
	

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		jsonRestaurante = ResourceUtils.getContentFromResource("/json/correto/restaurante.json");
		
		jsonRestauranteSemFrete = ResourceUtils.getContentFromResource("/json/incorreto/restauranteSemFrete.json");
		jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource("/json/incorreto/restauranteSemCozinha.json");
		jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource("/json/incorreto/restauranteCozinhaInexistente.json");
		
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
    	RestAssured.given()
            .body(jsonRestauranteSemFrete)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }
    
    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
    	RestAssured.given()
            .body(jsonRestauranteSemCozinha)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }
    
    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
    	RestAssured.given()
            .body(jsonRestauranteComCozinhaInexistente)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
		RestAssured.given()
						.accept(ContentType.JSON)
					.when()
						.get()
					.then()
						.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConterQtdRestaurantes_QuandoConsultarRestaurantes() {
		RestAssured.given()
						.accept(ContentType.JSON)
					.when()
						.get()
					.then()
						.body("", Matchers.hasSize(quantidadeRestaurantesCadastrados))
						.body("nome", Matchers.hasItems("Thai Gourmet", "Thai Delivery"));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
		RestAssured.given()
						.body(jsonRestaurante)
						.contentType(ContentType.JSON)
						.accept(ContentType.JSON)
					.when()
						.post()
					.then()
						.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarRestauranteExistente() {
		RestAssured.given()
						.pathParam("id", restauranteThaiGoumet.getId())
						.accept(ContentType.JSON)
					.when()
						.get("/{id}")
					.then()
						.statusCode(HttpStatus.OK.value())
						.body("nome", equalTo(restauranteThaiGoumet.getNome()));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
		RestAssured.given()
						.pathParam("id", RESTAURANTE_ID_INEXISTENTE)
						.accept(ContentType.JSON)
					.when()
						.get("/{id}")
					.then()
						.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	
	private void prepararDados() {
		
		
		
		
		cozinhaThaiGoumet = new Cozinha();
		cozinhaThaiGoumet.setId(1L);
		cozinhaThaiGoumet.setNome("Indiana");
		cozinhaRepository.save(cozinhaThaiGoumet);
		cozinhaThaiGoumet = cozinhaRepository.findById(1L).get();
		
		restauranteThaiGoumet = new Restaurante();
		restauranteThaiGoumet.setNome("Thai Gourmet");
		restauranteThaiGoumet.setTaxaFrete(new BigDecimal(10));
		restauranteThaiGoumet.setCozinha(cozinhaThaiGoumet);;
		restauranteRepository.save(restauranteThaiGoumet);
		
		
		cozinhaThaiDelivery = new Cozinha();
		cozinhaThaiDelivery.setId(2L);
		cozinhaThaiDelivery.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaThaiDelivery);
		cozinhaThaiDelivery = cozinhaRepository.findById(2L).get();
		
		restauranteThaiDelivery = new Restaurante();
		restauranteThaiDelivery.setNome("Thai Delivery");
		restauranteThaiDelivery.setTaxaFrete(new BigDecimal(10));
		restauranteThaiDelivery.setCozinha(cozinhaThaiDelivery);
		restauranteRepository.save(restauranteThaiDelivery);

		
		quantidadeRestaurantesCadastrados = (int) restauranteRepository.count();
	}
	
	

}
