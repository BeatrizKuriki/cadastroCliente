package com.alencarkuriki.projeto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alencarkuriki.projeto.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	
	

}
