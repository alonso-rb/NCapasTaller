package com.rponce.Parcial2.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SongDTO {
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String duration;
	
	public SongDTO(String title, String duration) {
		super();
		this.title = title;
		this.duration = duration;
	}

}
