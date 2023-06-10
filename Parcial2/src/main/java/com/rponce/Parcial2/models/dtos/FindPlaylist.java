package com.rponce.Parcial2.models.dtos;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindPlaylist {
	private UUID code;
	
	private String title;
	
	private String description;
	
	private List<FindSongPlaylistDTO> songs;
	
	private String duration;
}
