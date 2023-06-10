package com.rponce.Parcial2.services.implementations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rponce.Parcial2.models.dtos.UserLoginDTO;
import com.rponce.Parcial2.models.dtos.SaveUserDTO;
import com.rponce.Parcial2.models.entities.User;
import com.rponce.Parcial2.repositories.UserRepository;
import com.rponce.Parcial2.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void RegisterUser(SaveUserDTO info) throws Exception {
		
		User user = new User(
					info.getUsername(),
					info.getEmail(),
					passwordEncoder.encode(info.getPassword())
				);
		
		userRepository.save(user);
	}

	@Override
	public User getUserByCredentials(String email, String username) {
		User userbyIdentifier = userRepository.findOneByEmailOrUsername(email, username);
		
		return userbyIdentifier;
	}

	@Override
	public User AuthUser(UserLoginDTO info) {
		User userbyIdentifier = userRepository.findOneByEmailOrUsername(info.getIdentifier(), info.getIdentifier());
		
		Boolean match = passwordEncoder.matches(info.getPassword(), userbyIdentifier.getPassword());
		
		if(match == true) {
			return userbyIdentifier;
		}
		
		return null;
	}

}
