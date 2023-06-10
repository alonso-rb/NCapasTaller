package com.rponce.Parcial2.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Parcial2.models.entities.User;

public interface UserRepository 
				extends ListCrudRepository <User, UUID>{
	User findOneBycode(UUID id);
	User findOneByEmailOrUsername(String email, String Username);
}
