package com.alencarkuriki.projeto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alencarkuriki.projeto.entities.Client;



public interface ClientRepository extends JpaRepository<Client, Long>{
	
	

}
