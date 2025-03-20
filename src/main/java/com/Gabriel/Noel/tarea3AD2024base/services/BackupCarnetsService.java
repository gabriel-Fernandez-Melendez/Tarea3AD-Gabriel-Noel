package com.Gabriel.Noel.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.controller.ExportarCarnetXMLController;
import com.Gabriel.Noel.tarea3AD2024base.dataeXistdb.ExistdbConnection;
import com.Gabriel.Noel.tarea3AD2024base.modelo.BackupCarnets;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Carnet;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Parada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.ParadaSellada;
import com.Gabriel.Noel.tarea3AD2024base.modelo.Peregrino;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.jpa.CarnetRepository;
import com.Gabriel.Noel.tarea3AD2024base.repositorios.mongo.BackupCarnetsRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class BackupCarnetsService {
	@Autowired
	private BackupCarnetsRepository backupCarnetsRepository;
	@Autowired
	private PeregrinoService peregrino_service;

	public void generarBackupCarnets() 
	{
		Date fecha = new Date();
		
		// Format the date and time
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fecha_formated = formatter.format(fecha);
		
		String nombreArchivo = "backupcarnets"+"<"+fecha_formated+">";
		
		List<String> carnets_xml = new ArrayList<>();
		List<Peregrino> peregrinos = peregrino_service.ListaDePeregrinos();
		
		for (Peregrino p : peregrinos) 
		{
			String xml = ExistdbConnection.exportarCarnet(p);			
			carnets_xml.add(xml);
		}

		BackupCarnets backup = new BackupCarnets(nombreArchivo,fecha_formated, carnets_xml);
		backupCarnetsRepository.save(backup);
	
		
	}

}
