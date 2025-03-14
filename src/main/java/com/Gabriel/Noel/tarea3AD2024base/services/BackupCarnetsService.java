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
	private CarnetService carnet_service;
	@Autowired
	private PeregrinoService peregrino_service;
	@Autowired
	private ParadaService parada_service;
	@Autowired
	private ParadaSelladaService parada_sellada_service;

	public void generarBackupCarnets() {
		Date fecha = new Date();
		// Format the date and time
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fecha_formated = formatter.format(fecha);
		// y ahora la lista de carnets
		List<Carnet> carnets = carnet_service.ListaDeCarnets();
		List<String> carnets_xml = new ArrayList<>();
		List<Peregrino> peregrinos = peregrino_service.ListaDePeregrinos();
		ArrayList<Parada> paradas_filtradas = new ArrayList<>();
		ArrayList<ParadaSellada> paradas_selladas_filtradas = new ArrayList<>();
		for (Peregrino p : peregrinos) {
			for (Carnet c : carnets) {
				if (p.getCarnet().getId() == c.getId()) {
					List<Parada> paradas = parada_service.ListaDeParadas();
					for (Parada paradas_aux : paradas) {
						if (paradas_aux.getId() == c.getId()) {
							paradas_filtradas.add(paradas_aux);
							List<ParadaSellada> paradas_selladas = new ArrayList<>();
							paradas_selladas = parada_sellada_service.obtenerTodasParadasSelladas();
							for (ParadaSellada par : paradas_selladas) {
								if (paradas_aux.getId() == par.getParada().getId()) {
									paradas_selladas_filtradas.add(par);
									String xml = ExistdbConnection.exportarCarnet(p, paradas_filtradas,
											paradas_selladas_filtradas);
									carnets_xml.add(xml);
								}
								
							}
						}
					}
				}
			

			}
		}

		BackupCarnets backup = new BackupCarnets(fecha_formated, carnets_xml);
		backupCarnetsRepository.save(backup);
	}

}
