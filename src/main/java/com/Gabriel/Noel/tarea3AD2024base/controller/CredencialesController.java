package com.Gabriel.Noel.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;

@Component
public class CredencialesController {

private final CredencialesService credenciales_service;
	
	@Autowired
	public CredencialesController(CredencialesService c) {
		this.credenciales_service=c;
	}
	//no puedo usar static por que  no llega correctamente la inyeccion de dependencias
	public void prueba() {
		System.out.println("prueba");
		Credenciales cred = new Credenciales();
		cred.setNombreUsuario("admin");
		cred.setContraseñaUsuario("admin");
		cred.setTipo(Usuarios.Peregrino	);
		credenciales_service.GuardarCredenciales(cred);
		System.out.println("termino");
	}
}

