package com.alencarkuriki.projeto.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alencarkuriki.projeto.dto.ClientDTO;
import com.alencarkuriki.projeto.entities.Client;
import com.alencarkuriki.projeto.services.ClientService;
/*
 * O RESOURCE IMPLEMENTA O CONTROLADOR REST. O BACKEND VAI DISPONIBILIZAR UMA API(APPLICATION PROGRAMING
 * INTERFACE) SÃO OS RECURSOS QUE VC VAI DISPONIBILIZAR PARA AS APLICAÇÕES UTILIZAREM
 * A API É IMPLEMENTADA POR DOS CONTROLADORES REST- O TERMO CONCEITUAL TÉCNICO QUE USAMOS PRA ISSO
 * É RESOURCE POR ISSO A CLASSE CHAMA CLIENTRESOURCE- ESTA SERIA A PRIMEIRA CAMADA ONDE OS FRONTEND CONVERSA
 * E FAZ SUAS REQUISIÇÕES PARA O BACK, QUE POR SUA VEZ SÃO ATENDIDAS PELA API.
 * OS RECURSOS DA APLICAÇÃO SÃO IMPLEMENTADOS PELOS CONTROLADORES.
 * É COMO SE O RECURSO FOSSE O CONCEITO E O CONTROLADOR A FORMA DE IMPLEMENTAR ISSO
 * SEMPRE QUE UM RECURSO TIVER A VER COM UMA ENTIDADE(POR ENTIDADE ENTENDA DADOS QUE SERÃO
 * COLOCADOS NO BANCO DE DADOS) A GENTE COLOCA O NOME DA ENTIDADE E NA SEQUENCIA O NOME RESOURCE
 * ISSO VAI INDICAR QUE ESTA CLASSE SERÁ UM RECURSO DAQUELA ENTIDADE.
 * PARA CONFIGURAR 	QUE ESTÁ CLASSE VAI SER UM CONTROLADOR REST E VAI RESPONDER AS REQUISIÇÕES
 * COLOCAMOS UM ANNOTION CHAMADO @RESTCONTROLER(COMO VEMOS ABAIXO)
 * ANNOTATION É UM FORMA SIMPLES DE ENXUTA DE CONFIGURAR ALGUMA COISA NO CÓDIGO, QUE JÁ VEM IMPLEMENTADA NO SPRING JAVA
 * O ANNOTATION JÁ TRAZ TODA A INFRAESTRUTURA NECESSÁRIA SEM A NECESSIDADE DE SE FAZER A PROGRAMAÇÃO NA MÃO
 * O ANNOTATION VAI REALIZAR UM PROCESSAMENTO POR BAIXO DOS PANOS AO COMPILAR A CLASSE E SERÁ DISPONIBILIZADA
 * COMO RECURSO.
 * SEMPRE QUE VC QUISER IMPLEMENTAR ALGUMA FEATURE NO SPRING VC VAI UTILIZAR O ANNOTATION
 * A SEGUDA ANNOTATION- @REQUESTMAPPING: VAMOS COLOCAR QUAL É A ROTA DO SEU RECURSO. E GERALMENTE
 * ESSA ROTA É ESCRITO NO PLURAL
 * 
 * 
 */



@RestController
@RequestMapping(value = "/clients")
 class ClientResource {
	/*
	 * AQUI IREMOS CRIAR NOSSO PRIMEIRO ENDPOINT, OU SEJA A PRIMEIRA ROTA POSSÍVEL QUE VAI 
	 * RESPONDER ALGUMA COISA PARA O FRONT
	 * AQUI TERMOS VÁRIOS ENDPOINTS, UM PRA SALVAR, BUSCAR , ATUALIZAR UMA CATEGORIA.
	 * 
	 */
	@Autowired
	private ClientService service;
	
	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll(
			/*
			 * ACIMA CRIAMOS UM MÉTIDO QUE SEU TIPO DE RETORNO SERÁ UM RESPONSEENTITY(DO TIPO GENERIC)- QUE NADA MAIS
			 * É QUE UM OBJETO DO SPRING QUE VAI ENCAPSULAR UMA RESPOSTA HTTP.
			 * E ENTRE <> VC DIZ QUAL O TIPO DE DADA ESTARÁ NO PAYLOAD(CORPO DA RESPOSTA)
			 */
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "ASC") String direction,
			@RequestParam(value = "direction", defaultValue = "name") String orderBy
			
			){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy );
		
		
		Page<ClientDTO> list = service.findAllPaged(pageRequest);		
		return ResponseEntity.ok().body(list);
		
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
		ClientDTO dto = service.findById(id);		
		return ResponseEntity.ok().body(dto);

}
	
	@PostMapping
	public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
		
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO dto){
		dto = service.update(id, dto);
		
		return ResponseEntity.ok().body(dto);
	
}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
	


