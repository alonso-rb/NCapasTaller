package com.rponce.Parcial2.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Parcial2.models.dtos.SongDTO;
import com.rponce.Parcial2.models.entities.Song;
import com.rponce.Parcial2.services.SongService;
import com.rponce.Parcial2.utils.TimeFormat;


@RestController
@RequestMapping("/Song")
@CrossOrigin("*")
public class SongController {
	@Autowired
	private SongService songService;
	
	@Autowired
	private TimeFormat timeFormat;

	//GET ALL SONGS BY TITLE  
	@GetMapping("/")
	public ResponseEntity<?> searchSongs(@RequestParam(name = "title") String title) {
		
		List<Song> songsByTitle; 
		
		List<SongDTO> format = new ArrayList<>();
						
		if(title == null) {
			songsByTitle = songService.findAll();
		} else {
			
			songsByTitle = songService.findByTitleContainingIgnoreCase(title);

		}
		
		for(Song song: songsByTitle) {
			
			format.add(new SongDTO(
					song.getTitle(),
					timeFormat.FormatToMMSS(song.getDuration())
					));
		}
		
		return new ResponseEntity<>(format, HttpStatus.OK);
	}
}