package com.alencarkuriki.projeto.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alencarkuriki.projeto.dto.ClientDTO;
import com.alencarkuriki.projeto.entities.Client;
import com.alencarkuriki.projeto.repositories.ClientRepository;
import com.alencarkuriki.projeto.services.exceptions.DataBaseException;
import com.alencarkuriki.projeto.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	@Autowired
	private ClientRepository repository;
	

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageResquest) {
		Page<Client> list = repository.findAll(pageResquest);

		return list.map(x -> new ClientDTO(x));

	
	}
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		return  new ClientDTO(entity);
	}
	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyDtoToEntity(dto, entity);
		//entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {	
		try {
		Client entity = repository.getOne(id);
		copyDtoToEntity(dto, entity);
		//entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ClientDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not Found" + id);
			
		}
		
	
	}
	
	public void delete(Long id) {
		try {
		repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found "+id);
		}
		
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException ("Integrity violation.");
		}
		}
		private void copyDtoToEntity(ClientDTO dto, Client entity) {
			entity.setName(dto.getName());
			entity.setCpf(dto.getCpf());
			entity.setIncome(dto.getIncome());
			entity.setBirthDate(dto.getBirthDate());
			entity.setChildren(dto.getChildren());
			
			
			
			
			
			
		
		
	}
	




	
}
