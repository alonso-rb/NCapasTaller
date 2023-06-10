package com.rponce.Parcial2.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindPlaylistByUserDTO {
	
	@NotEmpty(message = "Code is required")
	private String code;
	
	@NotEmpty(message = "Title is required")
	private String title;
	
	@NotEmpty(message = "Description is required")
	private String description;
	
	private String username;
	
}
