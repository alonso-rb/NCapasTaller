package com.rponce.Parcial2.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SongXPlaylistSaveDTO {
	
	@NotEmpty
	//@Pattern(regexp = "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$")
	private String songCode;
	
}
