package com.Gabriel.Noel.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.Gabriel.Noel.tarea3AD2024base.controller.ExportarCarnetXMLController;
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
    private CarnetService c;
    @Autowired
    private PeregrinoService p;
    @Autowired
    private ParadaService paradaservice;
    @Autowired
    private ParadaSelladaService sellado;
    public void generarBackupCarnets() {
    	 Date currentDate = new Date();
         System.out.println("Current Date and Time: " + currentDate);

         // Format the date and time
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         String formattedDate = formatter.format(currentDate);
         //y ahora la lista de carnets
         List<Peregrino> peregrinos =p.ListaDePeregrinos();
         for (Peregrino c: peregrinos) {

     		ArrayList<Parada> par = (ArrayList<Parada>) paradaservice.ListaDeParadas();
     		ArrayList<ParadaSellada> selladas=(ArrayList<ParadaSellada>) sellado.obtenerTodasParadasSelladas();
        	 ExportarCarnetXMLController.exportarCarnet(c, par, selladas);
         }
         List<String> carnets_xml=null;
        BackupCarnets backup = new BackupCarnets(formattedDate,carnets_xml);
        backupCarnetsRepository.save(backup);
    }

}
