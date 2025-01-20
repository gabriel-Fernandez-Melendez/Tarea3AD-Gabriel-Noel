package com.Gabriel.Noel.tarea3AD2024base.controller;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Usuarios;
import com.Gabriel.Noel.tarea3AD2024base.services.CredencialesService;

public class CredencialesController {

	public  static void prueba() {
		System.out.println("prueba");
		Credenciales cred = new Credenciales();

	
		cred.setId(1L);
		cred.setNombreUsuario("admin");
		cred.setContraseñaUsuario("admin");
		cred.setTipo(Usuarios.Peregrino	);

		CredencialesService vamos=new CredencialesService();
		vamos.GuardarCredenciales(cred);
		System.out.println("termino");
	}
}
