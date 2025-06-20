package com.liverpool.users.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(name = "nombre_apellidoPaterno_email_idx", def = "{'nombre': 1, 'apellidoPaterno': 1, 'email': 1}", unique = true)
public class Cliente {
    @Id
    private String id;
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "El campo debe contener solo letras y espacios")
    @Schema(example = "juan")
    private String nombre;
    @NotBlank(message = "El apellido paterno no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "El campo debe contener solo letras y espacios")
    @Schema(example = "perez")
    private String apellidoPaterno;
    @NotBlank(message = "El apellido materno no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "El campo debe contener solo letras y espacios")
    @Schema(example = "garcia")
    private String apellidoMaterno;
    @Email(message = "Correo inválido")
    @Schema(example = "juan.perez@correo.com")
    private String email;
    @NotBlank(message = "La dirección no puede estar vacía")
    @Schema(example = "Av. Reforma #123, CDMX")
    private String direccion;
}
