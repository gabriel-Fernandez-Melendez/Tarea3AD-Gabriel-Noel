package com.Gabriel.Noel.tarea3AD2024base.modelo;

public enum Usuarios {
	// valores de las credenciales del usuario
	Invitado("invitado"), Responsable_Parada("Responsable_parada"), Peregrino("Peregrino"),
	Administrador_General("Administrador_general");
	//atributo String del valor de la enum
	private String tipo_usuario;

	// constructor de la enum
	private Usuarios(String tipo_usuario) {
		this.tipo_usuario = tipo_usuario;
	}

	// getter del valor de la enum en formato String
	public String getTipoDeUsuario() {
		return tipo_usuario;
	}
}
