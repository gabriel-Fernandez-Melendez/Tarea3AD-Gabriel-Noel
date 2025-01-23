package com.Gabriel.Noel.tarea3AD2024base.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.modelo.Credenciales;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.CredencialesRepository;

@Service
public class CredencialesService {
		
	
	
	//he logrado que se implemente con autowired para hacer pruebas unitarias de mi codigo 
		@Autowired
		private CredencialesRepository credencialesBD;
		
		

	//metodos de recuperacion de datos haciendo uso del repositorio de las credenciales
	public Credenciales GuardarCredenciales(Credenciales c) {
		return credencialesBD.save(c);
	}
	//metodo para recibir la lista de credenciales desde la base de datos
	public List<Credenciales> ListaDeCredenciales() {
		return credencialesBD.findAll();
	}
	
	//apartir de aqui  implementare mas metodos en casi de ser necesario
	
	// Prueba NOEL (RETORNA LAS CREDENCIALES PERTENECIENTES AL NOMBRE DEL USUARIO UNICO)
	public Credenciales obtenerCredencialesPorNombreUsuario(String nombreUsuario)
	{
		return credencialesBD.findByNombreUsuario(nombreUsuario);
	}
        		
	
}
