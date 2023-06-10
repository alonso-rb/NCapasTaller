package com.rponce.Parcial2.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveUserDTO {
	
	@NotEmpty
	private String email;
	
	@NotEmpty
	@Size(min = 5, message = "Usuario debe tener como mínimo 5 caracteres")
	private String username;
	
	@NotEmpty
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$", message = "Contraseña debe tener como mínimo:"
			+ " un número, un caracter especial, una mayúscula, una minúscula y como mínimo 8 caracteres.")
	private String password;
	
}
