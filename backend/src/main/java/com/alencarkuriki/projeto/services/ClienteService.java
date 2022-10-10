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

import com.alencarkuriki.projeto.dto.ClienteDTO;
import com.alencarkuriki.projeto.entities.Cliente;
import com.alencarkuriki.projeto.repositories.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repository;
	

	@Transactional(readOnly = true)
	public Page<ClienteDTO> findAllPaged(PageRequest pageResquest) {
		Page<Cliente> list = repository.findAll(pageResquest);

		return list.map(x -> new ClienteDTO(x));

	
	}
	@Transactional(readOnly = true)
	public ClienteDTO findById(Long id) {
		Optional<Cliente> obj = repository.findById(id);
		Cliente entity = obj.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		return  new ClienteDTO(entity, entity.getCategories());
	}
	@Transactional
	public ClienteDTO insert(ClienteDTO dto) {
		Cliente entity = new Cliente();
		copyDtoToEntity(dto, entity);
		//entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ClienteDTO(entity);
	}
	
	@Transactional
	public ClienteDTO update(Long id, ClienteDTO dto) {	
		try {
		Cliente entity = repository.getOne(id);
		copyDtoToEntity(dto, entity);
		//entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ClienteDTO(entity);
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
		private void copyDtoToEntity(ClienteDTO dto, Cliente entity) {
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());
			entity.setDate(dto.getDate());
			entity.setImgUrl(dto.getImgUrl());
			entity.setPrice(dto.getPrice());
			
			
			
			
			
			
		
		
	}
	




	
}
