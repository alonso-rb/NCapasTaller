package com.rponce.Parcial2.services;

import com.rponce.Parcial2.models.dtos.UserLoginDTO;
import com.rponce.Parcial2.models.dtos.SaveUserDTO;
import com.rponce.Parcial2.models.entities.User;

public interface UserService {
	void RegisterUser(SaveUserDTO info) throws Exception;
	User getUserByCredentials(String email, String username);
	User AuthUser(UserLoginDTO info);
}
