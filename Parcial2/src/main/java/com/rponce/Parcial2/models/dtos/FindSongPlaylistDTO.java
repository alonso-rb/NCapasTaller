package com.rponce.Parcial2.models.dtos;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindSongPlaylistDTO {
	private UUID code;
	
	private String title;
	
	private String duration;
	
	private Date dateAdded;
}
