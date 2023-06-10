
package com.rponce.Parcial2.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Parcial2.models.dtos.MessageDTO;
import com.rponce.Parcial2.models.dtos.SavePlaylistDTO;
import com.rponce.Parcial2.models.dtos.SongXPlaylistSaveDTO;
import com.rponce.Parcial2.models.dtos.FindSongPlaylistDTO;
import com.rponce.Parcial2.models.dtos.FindPlaylist;
import com.rponce.Parcial2.models.entities.Playlist;
import com.rponce.Parcial2.models.entities.User;
import com.rponce.Parcial2.models.entities.Song;
import com.rponce.Parcial2.models.entities.SongXPlaylist;
import com.rponce.Parcial2.services.PlaylistService;
import com.rponce.Parcial2.services.SongService;
import com.rponce.Parcial2.services.SongXPlaylistService;
import com.rponce.Parcial2.services.UserService;
import com.rponce.Parcial2.utils.ErrorHandlers;
import com.rponce.Parcial2.utils.TimeFormat;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/playlist")
@CrossOrigin("*")
public class PlaylistController {

	@Autowired
	private PlaylistService playlistService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SongService songService;
	
	@Autowired
	private SongXPlaylistService songXplaylistService;
	
	@Autowired
	private TimeFormat timeFormat;
	
	@Autowired
	private ErrorHandlers errorHandler;
	
	
	@PostMapping("/")
	public ResponseEntity<?> savePlaylist(@ModelAttribute @Valid SavePlaylistDTO info, BindingResult validations) {	
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()),
					HttpStatus.BAD_REQUEST);
		} 

		User userFound = userService.getUserByCredentials(info.getUser(), info.getUser());
		
		if(userFound == null){
			return new ResponseEntity<>(new MessageDTO("User not found"), HttpStatus.NOT_FOUND);
		}
		
		try {
			playlistService.save(info, userFound);

			return new ResponseEntity<>(
					new MessageDTO("Playlist created"), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(
					new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/{code}")
	public ResponseEntity<?> addSongToPlaylist(@ModelAttribute @Valid SongXPlaylistSaveDTO data, @PathVariable(name = "code") UUID code, BindingResult validations){
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()),
					HttpStatus.BAD_REQUEST);
		} 
		Song song = songService.findOneById(data.getSongCode().toString());
		Playlist playlist = playlistService.findOneById(code.toString());
		
		if(song == null)
			return new ResponseEntity<>(new MessageDTO("Song not found"), HttpStatus.NOT_FOUND);
		
		if(playlist == null)
			return new ResponseEntity<>(new MessageDTO("Playlist not found"), HttpStatus.NOT_FOUND);
		
		Date date = new Date();
	    
		List<SongXPlaylist> songXplaylist = songXplaylistService.findByPlaylist(playlist);
		
		SongXPlaylist repeated = songXplaylist.stream().filter(s->(s.getSong().getCode()==song.getCode()))
				.findAny()
				.orElse(null);
		if(!(repeated == null)) {
			return new ResponseEntity<>(new MessageDTO("The song is already added"), HttpStatus.BAD_REQUEST);
		}
			
		try {
			songXplaylistService.createSongXPlaylist(date, playlist, song);
			return new ResponseEntity<>(
					new MessageDTO("Song Added"), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(
					new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<?> searchPlaylistWithTheSong(@PathVariable String code) {
		Playlist playlist = playlistService.findOneById(code);
		
		if(playlist == null) 
			return new ResponseEntity<>("playlist not found", HttpStatus.NOT_FOUND);
		
		List<SongXPlaylist> songXplaylist = playlist.getPlaylistSongs();
		List<FindSongPlaylistDTO> songs = new ArrayList<>();
		Integer full_duration = 0;
		
		songXplaylist.stream().forEach((s)-> {
			String duration = timeFormat.FormatToMMSS(s.getSong().getDuration());
			FindSongPlaylistDTO song = new FindSongPlaylistDTO(s.getSong().getCode(), s.getSong().getTitle(), duration, s.getDateAdded());
			songs.add(song);
		});
		
		for(SongXPlaylist s: songXplaylist){
			full_duration = full_duration + s.getSong().getDuration();
		}
		FindPlaylist showPlaylist = new FindPlaylist(playlist.getCode(), playlist.getTitle(), playlist.getDescription(), songs, timeFormat.FormatToMMSS(full_duration));
		return new ResponseEntity<>(showPlaylist, HttpStatus.OK);
	}
}
