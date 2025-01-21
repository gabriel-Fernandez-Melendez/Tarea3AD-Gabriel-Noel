package com.Gabriel.Noel.tarea3AD2024base.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;

@Controller
public class CredencialesController {

	@Autowired
	private CredencialesService credenciales_service;
	
	//no puedo usar static por que  no llega correctamente la inyeccion de dependencias
	public void prueba() {
		System.out.println("prueba");
		Credenciales cred = new Credenciales();
		cred.setNombreUsuario("rtyhnetd");
		cred.setContraseñaUsuario("pero q");
		cred.setTipo(Usuarios.Peregrino	);
		credenciales_service.GuardarCredenciales(cred);
		System.out.println("termino");
	}
	
	
}

