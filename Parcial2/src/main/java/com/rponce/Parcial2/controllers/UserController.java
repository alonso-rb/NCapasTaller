package com.rponce.Parcial2.controllers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Parcial2.models.dtos.FindPlaylistByUserDTO;
import com.rponce.Parcial2.models.dtos.MessageDTO;
import com.rponce.Parcial2.models.dtos.SaveUserDTO;
import com.rponce.Parcial2.models.dtos.UserLoginDTO;
import com.rponce.Parcial2.models.entities.Playlist;
import com.rponce.Parcial2.models.entities.User;
import com.rponce.Parcial2.services.PlaylistService;
import com.rponce.Parcial2.services.UserService;
import com.rponce.Parcial2.utils.ErrorHandlers;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PlaylistService playlistService;
	
	@Autowired
	private ErrorHandlers errorHandler;
	
	@PostMapping("/auth/signup")
	private ResponseEntity<?> SignUpUser (@ModelAttribute @Valid SaveUserDTO info, BindingResult validations){
		User userCheckedByEmail = userService.getUserByCredentials(info.getEmail(), info.getEmail());
		User userCheckedByUsername = userService.getUserByCredentials(info.getUsername(), info.getUsername());
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(
						errorHandler.mapErrors(validations.getFieldErrors()),
						HttpStatus.BAD_REQUEST
					);
		}
		
		if(userCheckedByEmail!=null || userCheckedByUsername!=null) {
			return new ResponseEntity<>(new MessageDTO("Usuario ya se encuentra registrado"),
					HttpStatus.BAD_REQUEST);
		}
		
		try {
			userService.RegisterUser(info);
			return new ResponseEntity<>(new MessageDTO("Usuario registrado"), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/playlist")
	private ResponseEntity<?> findPlaylistByUser(String user, String title) {
		if (user == null) {
			return new ResponseEntity<>(new MessageDTO("User identifier is required"), HttpStatus.BAD_REQUEST);
		}
		
		User userFound = userService.getUserByCredentials(user, user);
		
		if(userFound == null){
			return new ResponseEntity<>(new MessageDTO("User not found"), HttpStatus.NOT_FOUND);
		}
		
		List<Playlist> playlistsFound = playlistService.findAllByUser(userFound);
		List<FindPlaylistByUserDTO> formatedPlaylists = new ArrayList<>();
		
		if (title == null) {
			playlistsFound.stream()
			.forEach((p) -> {
				FindPlaylistByUserDTO formatedPlaylist = new FindPlaylistByUserDTO(p.getCode().toString(), p.getTitle(), p.getDescription(), userFound.getUsername());
				
				formatedPlaylists.add(formatedPlaylist);
			});
		} else {
			playlistsFound = playlistsFound.stream()
					.filter(p -> p.getTitle().contains(title))
					.collect(Collectors.toList());
			
			playlistsFound.stream()
			.forEach((p) -> {
				FindPlaylistByUserDTO formatedPlaylist = new FindPlaylistByUserDTO(p.getCode().toString(), p.getTitle(), p.getDescription(), userFound.getUsername());
				
				formatedPlaylists.add(formatedPlaylist);
			});
		}

		return new ResponseEntity<>(formatedPlaylists, HttpStatus.OK);
	}
	
	@PostMapping("/auth/signin")
	private ResponseEntity<?> LoginUser(@ModelAttribute @Valid UserLoginDTO info, BindingResult validations){
		
		if(validations.hasErrors()) {
			return new ResponseEntity<>(errorHandler.mapErrors(validations.getFieldErrors()), 
					HttpStatus.BAD_REQUEST);
		}
		
		User userToLog = userService.AuthUser(info);
		
		if(userToLog == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			return new ResponseEntity<>(userToLog, HttpStatus.ACCEPTED);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}
