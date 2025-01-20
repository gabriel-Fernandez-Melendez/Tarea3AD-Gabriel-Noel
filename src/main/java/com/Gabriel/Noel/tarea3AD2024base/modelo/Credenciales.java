package com.Gabriel.Noel.tarea3AD2024base.modelo;

import jakarta.persistence.*; // Para las anotaciones JPA

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "credenciales") // Nombre de la tabla en la base de datos
public class Credenciales implements Serializable {

	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
	private Long id;

	@Column(name = "nombre_usuario", nullable = false, unique = true) // Columna única
	private String nombreUsuario;

	@Column(name = "contraseña_usuario", nullable = false) // No permitir nulos
	private String contraseñaUsuario;

	@Enumerated(EnumType.STRING) // Persistir el enum como String
	@Column(name = "tipo_usuario", nullable = false)
	private Usuarios tipo;

	

	// Constructor por defecto
	public Credenciales() {
	}

	// Constructor con parámetros
	public Credenciales(Long id, String nombreUsuario, String contraseñaUsuario, Usuarios tipo, Parada parada,
			Peregrino peregrino) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.contraseñaUsuario = contraseñaUsuario;
		this.tipo = tipo;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}
                                                                  
	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContraseñaUsuario() {
		return contraseñaUsuario;
	}

	public void setContraseñaUsuario(String contraseñaUsuario) {
		this.contraseñaUsuario = contraseñaUsuario;
	}

	public Usuarios getTipo() {
		return tipo;
	}

	public void setTipo(Usuarios tipo) {
		this.tipo = tipo;
	}
	
	//getters de la relacion onetoone
	

	// Métodos hashCode y equals
	@Override
	public int hashCode() {
		return Objects.hash(contraseñaUsuario, id, nombreUsuario, tipo);
	}

	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Credenciales other = (Credenciales) obj;
		return Objects.equals(contraseñaUsuario, other.contraseñaUsuario) && Objects.equals(id, other.id)
				&& Objects.equals(nombreUsuario, other.nombreUsuario) && tipo == other.tipo;
	}

	// Método toString
	@Override
	public String toString() {
		return "Usuarios [id=" + id + ", nombreUsuario=" + nombreUsuario + ", contraseñaUsuario=" + contraseñaUsuario
				+ ", tipo=" + tipo + "]";
	}
}
