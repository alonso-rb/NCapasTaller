package com.rponce.Parcial2.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SavePlaylistDTO {

	@NotEmpty(message = "Title is required")
	private String title;
	
	@NotEmpty(message = "Description is required")
	private String description;
	
	@NotEmpty(message = "Identifier is required")
	private String user;
	
}
